package com.panshicloud.base.dao.dos;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 枚举统计Do
 * </p>
 *
 * @author huangrankun
 */

@Data
public class DataOptionCountDo implements Serializable {

    /**
     * 领域
     */
    private String domain;

    /**
     * 数量
     */
    private Integer number;

}
