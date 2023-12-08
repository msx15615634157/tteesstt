package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author: xingxingfa
 * @since: 2022/11/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateDto {
    /**
     * 正确信息
     */
    String correct;

    /**
     * 错误信息
     */
    String error;
}
