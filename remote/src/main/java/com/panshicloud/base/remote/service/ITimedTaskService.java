package com.panshicloud.base.remote.service;

import java.util.Map;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年05月16日 15:06:39
 * @packageName com.panshicloud.base.remote.service
 * @className ITimedService
 * @describe 定时任务
 */
public interface ITimedTaskService {

    /**
     * 获取任务分组的定义
     *
     * @param groupCode 分组编码
     * @return String
     */
    String getGroup(String groupCode);

    /**
     * 执行任务
     *
     * @param code       任务编码
     * @param parameters 参数
     */
    void execute(String code, Map<String, Object> parameters);

}
