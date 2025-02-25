package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author shimengmeng
 * @since 2024/4/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("PSC_TIMED_TASK")
public class TimedTask {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
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
     * 任务类型
     */
    private String type;

    /**
     * 分组类型
     */
    private String groupType;

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
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    /**
     * 更新人时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单配置
     */
    private String configParams;
}
