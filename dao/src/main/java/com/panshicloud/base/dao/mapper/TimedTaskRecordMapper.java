package com.panshicloud.base.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panshicloud.base.dao.entity.TimedTaskRecord;
import org.springframework.stereotype.Component;

/**
 * 定时任务执行记录
 *
 * @author shimengmeng
 * @since 2024/4/30
 */
@Component
public interface TimedTaskRecordMapper extends BaseMapper<TimedTaskRecord> {
}
