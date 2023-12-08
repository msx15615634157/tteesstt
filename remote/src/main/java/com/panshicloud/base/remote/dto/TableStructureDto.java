package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author anyang
 * @version v0.1
 * @createTime 2023/3/712:37
 * @update [author] 2023/3/712:37
 */
@Data
public class TableStructureDto implements Serializable {

    /**
     * 表空间
     */
    private String tableSpace;

    /**
     * 表结构
     */
    private String tableStructure;
}
