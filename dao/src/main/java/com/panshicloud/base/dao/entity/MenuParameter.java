package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author luowenyao
 * @since 2022-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("psc_menu_parameter")
public class MenuParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 参数编码
     */
    private String code;

    /**
     * 参数值
     */
    private String value;

}
