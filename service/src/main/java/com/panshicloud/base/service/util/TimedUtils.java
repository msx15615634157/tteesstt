package com.panshicloud.base.service.util;

import com.alibaba.fastjson.JSON;
import com.panshicloud.base.remote.constants.TimedTaskCst;
import com.panshicloud.base.remote.dto.timed.QrtzDto;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.context.CommonContext;
import com.panshicloud.common.helper.ExceptionHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.*;

/**
 * 定时任务工具类
 *
 * @author fanzepu
 */
@Slf4j
public class TimedUtils {

    /**
     * 获取所有的定时任务
     *
     * @throws Exception
     */
    public static List<QrtzDto> getAllJob(Scheduler scheduler) {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        List<QrtzDto> jobList = new ArrayList();
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    QrtzDto job = new QrtzDto();
                    job.setJobName(jobKey.getName());
                    job.setGroupName(jobKey.getGroup());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setStatus(Trigger.TriggerState.NORMAL.equals(triggerState) ? 1 : 0);
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    String[] keys = jobDataMap.getKeys();
                    if (keys != null && keys.length > 0) {
                        Map<String, String> paramMap = new HashMap<>(keys.length, 1.0f);
                        for (String key : keys) {
                            paramMap.put(key, jobDataMap.get(key).toString());
                        }
                        String paramStr = JSON.toJSONString(paramMap);
                        job.setJobParam(paramStr);
                    }
                    Class<? extends Job> jobClass = jobDetail.getJobClass();
                    job.setJobClass(jobClass.getName());
                    jobList.add(job);
                }
            }

        } catch (SchedulerException e) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "获取所有的定时任务出现错误");
        }
        return jobList;
    }

    /**
     * 创建定时任务 定时任务创建之后默认启动状态
     *
     * @param scheduler     调度器
     * @param QuartzBeanDto 定时任务信息类
     * @throws Exception
     */
    public static void createScheduleJob(Scheduler scheduler, QrtzDto QuartzBeanDto) {
        try {
            // 校验表达式是否正确
            boolean validExpression = CronExpression.isValidExpression(QuartzBeanDto.getCronExpression());
            if (!validExpression) {
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "cron表达式不正确：" + QuartzBeanDto.getCronExpression());
            }
            //获取到定时任务的执行类  必须是类的绝对路径名称
            //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(QuartzBeanDto.getJobClass());
            // 构建定时任务信息
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass).withIdentity(QuartzBeanDto.getJobName(), QuartzBeanDto.getGroupName());

            // 设置执行时需要的参数
            jobBuilder.usingJobData(TimedTaskCst.SERVICE_CODE, QuartzBeanDto.getServiceCode());
            jobBuilder.usingJobData(TimedTaskCst.METHOD_CODE, QuartzBeanDto.getMethodCode());
            jobBuilder.usingJobData(TimedTaskCst.METHOD_NAME, QuartzBeanDto.getMethodName());
            jobBuilder.usingJobData(TimedTaskCst.METHOD_CLASS_PATH, QuartzBeanDto.getMethodClassPath());
            jobBuilder.usingJobData(TimedTaskCst.PARAMS, TimedTaskCst.PARAMS_NULL);
            jobBuilder.usingJobData(TimedTaskCst.OPERATE_USER_NAME, QuartzBeanDto.getOperateUserName());
            jobBuilder.usingJobData(TimedTaskCst.TASK_ID, QuartzBeanDto.getTaskId());
            jobBuilder.usingJobData(TimedTaskCst.OPERATE_COMMONCON_TEXT_USER, JSON.toJSONString(CommonContext.getUser()));
            List<Map> paramHashMaps = JSON.parseArray(QuartzBeanDto.getJobParam(), Map.class);
            if (!ObjectUtils.isEmpty(paramHashMaps)) {
                jobBuilder.usingJobData(TimedTaskCst.PARAMS, JSON.toJSONString(paramHashMaps));
            }
            JobDetail jobDetail = jobBuilder
                    .storeDurably()
                    .build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(QuartzBeanDto.getCronExpression());
                    // Cron表达式的触发器构建器失火策略：
                    // 1.withMisfireHandlingInstructionIgnoreMisfires：会执行一次补偿任务，并继续按正常的调度继续执行，保留之前的未能按时执行的次数, 不会丢失任何未能按时执行的次数，可以确保任务得以补偿执行，并且按照正常的调度继续执行，保持任务的连续性。
                    // 2.withMisfireHandlingInstructionFireAndProceed：会执行一次补偿任务，但会忽略之前未能按时执行的次数，任务会尽可能快地得到执行。
                    // 3.withMisfireHandlingInstructionDoNothing：当任务未能按时执行时，不做任何处理，等待下一个触发时间点再触发执行。这意味着如果任务错过了触发时间点，会等待下一个触发时间再次触发执行。
            scheduleBuilder.withMisfireHandlingInstructionDoNothing();
            // 构建触发器trigger
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(QuartzBeanDto.getJobName())
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "定时任务类路径出错：请输入类的绝对路径");
        } catch (SchedulerException e) {
            log.error("创建定时任务出错：" + e.getMessage());
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "创建定时任务出错");
        }
    }

    /**
     * 根据任务名称暂停定时任务
     *
     * @param scheduler 调度器
     * @param groupName 定时任务分组名称
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void pauseScheduleJob(Scheduler scheduler, String groupName, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("暂停定时任务出错：" + e.getMessage());
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "暂停定时任务出错");
        }
    }

    /**
     * 根据任务名称恢复定时任务
     *
     * @param scheduler 调度器
     * @param groupName 定时任务分组名称
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void resumeScheduleJob(Scheduler scheduler, String groupName, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("启动定时任务出错：" + e.getMessage());
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "启动定时任务出错");
        }
    }

    /**
     * 根据任务名称立即运行一次定时任务
     *
     * @param scheduler 调度器
     * @param groupName 定时任务分组名称
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void runOnce(Scheduler scheduler, String groupName, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("运行定时任务出错：" + e.getMessage());
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "运行定时任务出错");
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler     调度器
     * @param QuartzBeanDto 定时任务信息类
     * @throws SchedulerException
     */
    public static void updateScheduleJob(Scheduler scheduler, QrtzDto QuartzBeanDto) {
        deleteScheduleJob(scheduler, QuartzBeanDto.getGroupName(), QuartzBeanDto.getJobName());
        createScheduleJob(scheduler, QuartzBeanDto);
    }

    /**
     * 根据定时任务名称从调度器当中删除定时任务
     *
     * @param scheduler 调度器
     * @param groupName 定时任务分组名称
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void deleteScheduleJob(Scheduler scheduler, String groupName, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        try {
            if (ObjectUtils.isEmpty(jobKey)) {
                return;
            }
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(jobKey); // 删除任务

        } catch (SchedulerException e) {
            log.error("删除定时任务出错：" + e.getMessage());
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "删除定时任务出错");
        }
    }

    /**
     * 检查Job是否存在
     *
     * @throws Exception
     */
    public static Boolean isResume(Scheduler scheduler, String jobName, String jobGroupName) {
        Boolean state = false;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            state = scheduler.checkExists(triggerKey);
        } catch (SchedulerException e) {
            return state;
        }
        return state;
    }

    /**
     * 重启所有定时任务
     *
     * @param scheduler
     */
    public static void resumeAllScheduleJob(Scheduler scheduler) {
        List<QrtzDto> allJob = getAllJob(scheduler);
        allJob.forEach(job -> {
            resumeScheduleJob(scheduler, job.getGroupName(), job.getJobName());
        });

    }

    /**
     * 运行所有定时任务一次
     *
     * @param scheduler
     */
    public static void runAllOnce(Scheduler scheduler) {
        List<QrtzDto> allJob = getAllJob(scheduler);
        allJob.forEach(job -> {
            runOnce(scheduler, job.getGroupName(), job.getJobName());
        });
    }

    /**
     * 暂停所有任务
     *
     * @param scheduler
     */
    public static void pauseAllScheduleJob(Scheduler scheduler) {
        List<QrtzDto> allJob = getAllJob(scheduler);
        allJob.forEach(job -> {
            pauseScheduleJob(scheduler, job.getGroupName(), job.getJobName());
        });
    }

    /**
     * 删除所有任务
     *
     * @param scheduler
     */
    public static void deleteAllScheduleJob(Scheduler scheduler) {
        List<QrtzDto> allJob = getAllJob(scheduler);
        allJob.forEach(job -> {
            deleteScheduleJob(scheduler, job.getGroupName(), job.getJobName());
        });
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public static List<Map<String, Object>> queryRunJob(Scheduler scheduler) {
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 判断任务是否正在执行
     *
     * @param jobName   任务名
     * @param groupName 任务分组
     * @param scheduler
     * @return
     */
    public static Boolean isRunJob(Scheduler scheduler, String groupName, String jobName) {
        List<Map<String, Object>> runJobs = queryRunJob(scheduler);
        if (runJobs.isEmpty()) {
            return false;
        }
        runJobs.removeIf(item -> !String.valueOf(item.get("jobGroupName")).equals(groupName) && !String.valueOf(item.get("jobName")).equals(jobName));
        if (runJobs.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 获取任务执行数
     *
     * @param jobName   任务名
     * @param groupName 任务分组
     * @param scheduler
     * @return
     */
    public static Integer isRunJobSize(Scheduler scheduler, String groupName, String jobName) {
        List<Map<String, Object>> runJobs = queryRunJob(scheduler);
        if (runJobs.isEmpty()) {
            return 0;
        }
        runJobs.removeIf(item -> !String.valueOf(item.get("jobGroupName")).equals(groupName) && !String.valueOf(item.get("jobName")).equals(jobName));
        return runJobs.size();
    }

}
