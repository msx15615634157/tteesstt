package com.panshicloud.base.service.task;

import com.panshicloud.base.remote.service.IQueryIndexTempService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangrankun
 * @since 2022-04-8
 */

@Component
public class ScheduledTask {

    @DubboReference
    private IQueryIndexTempService queryIndexTemService;

    /**
     * 定时清空临时表
     */
    @Scheduled(cron = "0 0 0 */10 * ?")
    public void truncateTmp() {
        queryIndexTemService.truncateTmp();
        System.out.println(new Date() + "--清空多单位索引临时表任务执行！");
    }
}
