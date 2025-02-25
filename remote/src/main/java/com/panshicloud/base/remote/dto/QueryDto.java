package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: xingxingfa
 * @since: 2022/11/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryDto implements Serializable {
    /**
     * 头部字段名
     */
    List<String> head;
    /**
     * 字段对应数据
     */
    List<Map<String,String>> data;

    /**
     * 错误信息
     */
    String error;
}
