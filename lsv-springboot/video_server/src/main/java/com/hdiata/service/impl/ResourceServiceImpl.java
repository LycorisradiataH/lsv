package com.hdiata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.common.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.hdiata.mapper.ResourceMapper;
import com.hdiata.mapper.RoleResourceMapper;
import com.hdiata.pojo.dto.LabelOptionDTO;
import com.hdiata.pojo.dto.ResourceDTO;
import com.hdiata.pojo.entity.Resource;
import com.hdiata.pojo.entity.RoleResource;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.ResourceVO;
import com.hdiata.service.ResourceService;
import com.hdiata.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hdiata.common.constant.CommonConst.FALSE;

/**
 * 资源服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 18:40
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importSwagger() {
        // 删除所有资源
        this.remove(null);
        resourceMapper.delete(null);
        List<Resource> resourceList = new ArrayList<>();
        Map<String, Object> data =
                restTemplate.getForObject("http://localhost:8001/v2/api-docs", Map.class);
        // 获取所有模块
        List<Map<String, String>> tagsList = (List<Map<String, String>>) data.get("tags");
        tagsList.forEach(item -> {
            Resource resource = Resource.builder()
                    .resourceName(item.get("name"))
                    .isAnonymous(FALSE)
                    .build();
            resourceList.add(resource);
        });
        this.saveBatch(resourceList);
        Map<String, Integer> permissionMap = resourceList.stream()
                .collect(Collectors.toMap(Resource::getResourceName, Resource::getId));
        resourceList.clear();
        // 获取所有接口
        Map<String, Map<String, Map<String, Object>>> path =
                (Map<String, Map<String, Map<String, Object>>>) data.get("paths");
        path.forEach((url, value) -> value.forEach((requestMethod, info) -> {
            String permissionName = info.get("summary").toString();
            List<String> tag = (List<String>) info.get("tags");
            Integer parentId = permissionMap.get(tag.get(0));
            Resource resource = Resource.builder()
                    .resourceName(permissionName)
                    .url(url.replaceAll("\\{[^}]*\\}", "*"))
                    .parentId(parentId)
                    .requestMethod(requestMethod.toUpperCase())
                    .isAnonymous(FALSE)
                    .build();
            resourceList.add(resource);
        }));
        this.saveBatch(resourceList);
    }

    @Override
    public List<ResourceDTO> listResources(ConditionVO conditionVO) {
        // 查询资源列表
        List<Resource> resourceList = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                        Resource::getResourceName, conditionVO.getKeywords()));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 绑定模块下的所有接口
        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtils.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> childrenList =
                    BeanCopyUtils.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenList);
            childrenMap.remove(item.getId());
            return resourceDTO;
        }).collect(Collectors.toList());
        // 若还有资源未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, ResourceDTO.class))
                    .collect(Collectors.toList());
            resourceDTOList.addAll(childrenDTOList);
        }
        return resourceDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {
        // 更新资源信息
        Resource resource = BeanCopyUtils.copyObject(resourceVO, Resource.class);
        this.saveOrUpdate(resource);
        // 重新加载角色资源信息
        filterInvocationSecurityMetadataSource.clearDataSource();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteResource(Integer resourceId) {
        // 查询是否有角色关联
        Integer count = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getResourceId, resourceId));
        if (count > 0) {
            throw new ServiceException("该资源下存在角色");
        }
        // 删除子资源
        List<Integer> resourceIdList = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId).
                        eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        resourceIdList.add(resourceId);
        resourceMapper.deleteBatchIds(resourceIdList);
    }

    @Override
    public List<LabelOptionDTO> listResourceOption() {
        // 查询资源列表
        List<Resource> resourceList = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getIsAnonymous, FALSE));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 组装父子数据
        return parentList.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .map(resource -> LabelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * 获取所有资源模块
     * @param resourceList 资源列表
     * @return 资源模块列表
     */
    private List<Resource> listResourceModule(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }

    /**
     * 获取模块下的所有资源
     * @param resourceList 资源列表
     * @return 模块资源
     */
    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
    }

}
