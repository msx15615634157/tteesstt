package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xingxingfa
 * @since 2022/12/28
 */
@ApiModel("日志查询响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class OperationLogResponseVo implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("类型/模块")
    private String type;

    @ApiModelProperty("操作")
    private String operation;

    @ApiModelProperty("日志内容")
    private String logInfo;

    @ApiModelProperty("操作时间")
    private Date operationTime;

    @ApiModelProperty("操作组织机构Id")
    private String operationOrganizationId;

    @ApiModelProperty("操作用户id")
    private String operationUserId;

    @ApiModelProperty("接口名")
    private String interfaceName;

    @ApiModelProperty("本机IP")
    private String serverIp;

    @ApiModelProperty("客户IP")
    private String clientIp;

    @ApiModelProperty("客户名")
    private String clientCode;

    @ApiModelProperty("URI")
    private String uri;

    @ApiModelProperty("URL")
    private String url;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("查询字符串")
    private String queryString;
}
