package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 操作日志实体类
 * </p>
 *
 * @author huangrankun
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("PSC_OPERATION_LOG")
public class OperationLog {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
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
     * 操作时间实体
     */
    @TableField(fill = FieldFill.INSERT)
    private Date operationTime;

    /**
     * 操作组织机构Id
     */
    @TableField(fill = FieldFill.INSERT)
    private String operationOrganizationId;

    /**
     * 操作用户id
     */
    @TableField(fill = FieldFill.INSERT)
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
