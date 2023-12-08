package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单更新Dto
 * </p>
 *
 * @author yantao
 */

@Data
public class MenuUpdateDto {

    /**
     * id
     */
    private String id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单编码
     */
    private String code;
    /**
     * 菜单描述
     */
    private String description;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 菜单类型
     * <p>
     * 0:普通, 1:超链接, 2:预留
     */
    private Integer type;
    /**
     * 父级菜单
     */
    private String parentId;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 权重值
     */
    private Integer sort;
    /**
     * 路由名称
     */
    private String routerName;

    /**
     * 菜单参数
     */
    private List<MenuParameterDto> param;
}
