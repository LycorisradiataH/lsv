package com.hdiata.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 资源角色
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 19:50
 */
@Data
public class ResourceRoleDTO {

    /**
     * 资源id
     */
    private Integer id;

    /**
     * 路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 角色名
     */
    private List<String> roleList;

}
