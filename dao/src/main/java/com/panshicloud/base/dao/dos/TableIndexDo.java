package com.panshicloud.base.dao.dos;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字段类型
 * </p>
 *
 * @author yantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TableIndexDo {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 索引集合
     */
    private String indexName;
}
