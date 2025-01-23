package com.panshicloud.base.dao.dos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xingxingfa
 * @since 2025/1/6
 */
@Data
public class IndexAndIndexFieldDo implements Serializable {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 索引名
     */
    private String indexName;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 顺序
     */
    private Integer sort;

}
