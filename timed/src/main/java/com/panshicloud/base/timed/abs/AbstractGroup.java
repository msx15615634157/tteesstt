package com.panshicloud.base.timed.abs;


import com.alibaba.fastjson.JSON;
import com.panshicloud.base.timed.dto.TimedTaskDto;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务类型:抽象分组
 */
public abstract class AbstractGroup {

    /**
     * 服务标识
     *
     * @return String
     */
    public abstract String code();

    /**
     * 服务名称（前端展示）
     *
     * @return String
     */
    public abstract String name();

    /**
     * 任务实例集合
     * @return
     */
    public abstract List<AbstractTimedTask> timedTasks();

    /**
     * 定时方法信息
     */
    public List<TimedTaskDto> timedTaskInfos() {
        List<TimedTaskDto> rst = new ArrayList<>();
        for (AbstractTimedTask timedTask : this.timedTasks()) {
            rst.add(timedTask.info());
        }
        return rst;
    }

    /**
     * 执行方法
     * @param code
     * @param parameters
     */
    public void execute(String code, Map<String, Object> parameters) {
        for (AbstractTimedTask timedTask : this.timedTasks()) {
            if (StringUtils.equals(code, timedTask.code())) {
                timedTask.run(parameters);
            }
        }
    }

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", code());
        map.put("name", name());
        map.put("timedTaskInfos", JSON.toJSONString(timedTaskInfos()));
        return JSON.toJSONString(map);
    }

    /**
     * 添加校验
     *
     * @param str str
     * @return AbstractGroup
     */
    public static AbstractGroup build(String str) {
        Map<String, Object> map = JSON.parseObject(str, HashMap.class);
        return new AbstractGroup() {
            @Override
            public String code() {
                Object code = map.get("code");
                if (!(code instanceof String)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "编码参数必须为字符串");
                }
                return (String) code;
            }

            @Override
            public String name() {
                Object name = map.get("name");
                if (!(name instanceof String)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "名称必须为字符串");
                }
                return (String) name;
            }

            @Override
            public List<AbstractTimedTask> timedTasks() {
                // 无需反序列化
                return null;
            }

            @Override
            public List<TimedTaskDto> timedTaskInfos() {
                String taskMethodInfosStr = String.valueOf(map.get("timedTaskInfos"));
                List<TimedTaskDto> timedTaskDtos = JSON.parseArray(taskMethodInfosStr, TimedTaskDto.class);
                List<String> methodCodeList = new ArrayList<>();
                for (TimedTaskDto timedTaskDto : timedTaskDtos) {
                    if (methodCodeList.contains(timedTaskDto.getCode())) {
                        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "方法Code不能重复");
                    }
                    if (StringUtils.isBlank(timedTaskDto.getCode())) {
                        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "方法Code不能为空");
                    }
                    if (StringUtils.isBlank(timedTaskDto.getName())) {
                        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "方法名不能为空！");
                    }
                }
                return timedTaskDtos;
            }
        };
    }

}
