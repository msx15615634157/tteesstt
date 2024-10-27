package com.panshicloud.base.remote.dto;

import lombok.Data;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/29 9:51
 */
@Data
public class DirUpdateDto {
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
}
