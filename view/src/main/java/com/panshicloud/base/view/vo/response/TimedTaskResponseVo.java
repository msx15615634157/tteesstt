package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务响应类
 *
 * @author shimengmeng
 * @since 2024/4/29
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class TimedTaskResponseVo implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 任务CODE
     */
    private String type;

    /**
     * 任务名
     */
    private String typeName;

    /**
     * 分组类型CODE
     */
    private String groupType;

    /**
     * 分组名
     */
    private String groupName;

    /**
     * 执行用户(账号：默认用户、指定用户)
     */
    private String operateUserName;

    /**
     * 执行频率
     */
    private String cornExpression;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建人ID
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人ID
     */
    private String updateUserId;

    /**
     * 更新人时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单配置
     */
    private String configParams;

    /**
     * 下次执行时间
     */
    private String cornLatestTime;
}
