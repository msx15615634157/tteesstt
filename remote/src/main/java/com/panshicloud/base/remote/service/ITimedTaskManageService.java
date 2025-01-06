package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.timed.GroupTimedDefineDto;
import com.panshicloud.base.remote.dto.timed.GroupTimedInterfaceInfoDto;
import com.panshicloud.base.remote.dto.timed.TimedTaskDto;
import com.panshicloud.common.base.PageDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author shimengmeng
 * @since 2024/4/29
 */
public interface ITimedTaskManageService {

    /**
     * 新增
     *
     * @param code            编码
     * @param name            名称
     * @param type            类型
     * @param status          状态
     * @param operateUserName 执行用户
     * @param cornExpression  执行频率
     * @param remark          备注
     */
    void insert(String code, String name, String type,String groupType, String status, String operateUserName, String cornExpression, String remark, String configParams);

    /**
     * 查询
     *
     * @param code       编码
     * @param name       名称
     * @param type       类型
     * @param groupType  分组类型
     * @param status     状态
     * @param pageNumber 页数
     * @param pageSize   每页条数
     * @return
     */
    PageDto<TimedTaskDto> find(String groupType, String code, String name, String type, String status, Integer pageNumber, Integer pageSize);

    /**
     * 修改
     *
     * @param code            编码
     * @param name            名称
     * @param type            类型
     * @param status          状态
     * @param operateUserName 执行用户
     * @param cornExpression  corn表达式
     * @param remark          备注
     * @param id              主键ID
     * @param configParams    参数
     */
    void update(String code, String name, String type,String groupType, String status, String operateUserName, String cornExpression, String remark, String id, String configParams);

    /**
     * 修改参数
     *
     * @param id              主键ID
     * @param configParams    参数
     */
    void update(String id, String configParams);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    TimedTaskDto getById(String id);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 获取分组列表
     *
     * @return
     */
    List<GroupTimedDefineDto> findGroupDefine();

    /**
     * 获取分组参数
     *
     * @param code 服务标识
     * @return
     */
    List<GroupTimedInterfaceInfoDto> getGroupParameter(String code);

    /**
     * 启动定时任务
     *
     * @param id
     */
    void startUp(String id);

    /**
     * 暂停定时任务
     *
     * @param id
     */
    void stop(String id);

    /**
     * 立即运行一次
     *
     * @param id
     */
    void runOnce(String id);

    /**
     * 获取所有运行中的任务
     */
    List<Map<String, Object>> queryRunJob();

    /**
     * 当前任务是否正在运行
     *
     * @param groupName 分组
     * @param jobName   任务
     * @return true正在运行 false未运行
     */
    Boolean isRunJob(String groupName, String jobName);
}
