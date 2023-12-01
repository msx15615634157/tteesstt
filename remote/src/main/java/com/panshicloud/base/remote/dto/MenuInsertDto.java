package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单新增Dto
 * </p>
 *
 * @author yantao
 */

@Data
public class MenuInsertDto implements Serializable {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 菜单编码
     */
    private String code;

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
     * 路由名称
     */
    private String routerName;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 权重值
     */
    private Integer sort;

    /**
     * 当前排序
     */
    private Integer currentSort;

    /**
     * 菜单参数
     */
    private List<MenuParameterDto> param;

}
