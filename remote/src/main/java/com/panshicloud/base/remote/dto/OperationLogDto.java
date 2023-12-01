package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作日志Dto
 * </p>
 *
 * @author huangrankun
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperationLogDto implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 类型
     */
    private String type;

    /**
     * 操作
     */
    private String operation;

    /**
     * 日志内容
     */
    private String logInfo;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 操作组织机构Id
     */
    private String operationOrganizationId;

    /**
     * 操作用户id
     */
    private String operationUserId;

    /**
     * 接口名
     */
    private String interfaceName;

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
