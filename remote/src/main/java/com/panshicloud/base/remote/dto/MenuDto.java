package com.panshicloud.base.remote.dto;

import com.panshicloud.common.base.AbstractTree;
import com.panshicloud.common.helper.ConvertHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单Dto
 * </p>
 *
 * @author yantao
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDto extends AbstractTree<MenuDto> implements Serializable {

    /**
     * 物理主键
     */
    private String id;

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
     *
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
     * 功能id
     */
    private String functionId;

    /**
     * 路由路径
     */
    private String routerName;

    /**
     * 权重值
     */
    private Integer sort;


}
