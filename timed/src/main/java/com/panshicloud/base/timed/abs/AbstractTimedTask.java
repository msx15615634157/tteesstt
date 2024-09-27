package com.panshicloud.base.timed.abs;

import com.panshicloud.base.timed.dto.TimedTaskDto;

import java.util.Map;

public abstract class AbstractTimedTask {

    /**
     * 任务编码
     */
    public abstract String code();

    /**
     * 任务名称
     */
    public abstract String name();

    /**
     * 执行任务
     */
    public abstract void run(Map<String, Object> parameters);

    /**
     * 任务信息
     *
     * @return TimedTaskDto
     */
    public TimedTaskDto info() {
        return new TimedTaskDto(this.code(), this.name());
    }

}
