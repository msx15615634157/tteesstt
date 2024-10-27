package com.panshicloud.base.remote.dto;

import com.panshicloud.common.base.AbstractTree;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/29 10:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DirTreeDto extends AbstractTree<DirTreeDto> implements Serializable {
    /**
     * 目录编码
     */
    private String code;

    /**
     * 目录名称
     */
    private String name;

    /**
     * 目录类型
     */
    private String type;

    /**
     * 上级编码
     */
    private String parentCode;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户id
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新用户id
     */
    private String updateUserId;
}
