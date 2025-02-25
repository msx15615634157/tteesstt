package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: xingxingfa
 * @since: 2022/11/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateDto implements Serializable {
    /**
     * 正确信息
     */
    String correct;

    /**
     * 错误信息
     */
    String error;
}
