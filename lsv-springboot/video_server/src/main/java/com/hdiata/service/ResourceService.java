package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.LabelOptionDTO;
import com.hdiata.pojo.dto.ResourceDTO;
import com.hdiata.pojo.entity.Resource;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.ResourceVO;

import java.util.List;

/**
 * 资源服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 18:32
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 导入swagger权限
     */
    void importSwagger();

    /**
     * 查看资源列表
     * @param conditionVO 查询条件
     * @return 资源列表
     */
    List<ResourceDTO> listResources(ConditionVO conditionVO);

    /**
     * 添加或修改资源
     * @param resourceVO 资源对象
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 删除资源
     * @param resourceId 资源id
     */
    void deleteResource(Integer resourceId);

    /**
     * 查看资源选项
     * @return 资源选项
     */
    List<LabelOptionDTO> listResourceOption();
}
