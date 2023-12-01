package com.panshicloud.base.remote.dto;

import lombok.Data;

/**
 * @author xingxingfa
 * @since 2022/12/27
 */
@Data
public class RequestDto {
    /**
     * 本机IP
     */
    private String serverIp;

    /**
     * 客户IP
     */
    private String clientIp;

    /**
     * 客户名
     */
    private String clientCode;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * token
     */
    private String token;

    /**
     * 查询字符串
     */
    private String queryString;
}
