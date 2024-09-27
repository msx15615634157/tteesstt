package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.config.GroupTimed;
import com.panshicloud.base.config.TimedConfig;
import com.panshicloud.base.dao.entity.TimedTask;
import com.panshicloud.base.dao.mapper.TimedTaskMapper;
import com.panshicloud.base.remote.constants.TimedTaskCst;
import com.panshicloud.base.remote.dto.GroupDto;
import com.panshicloud.base.remote.dto.timed.GroupTimedDefineDto;
import com.panshicloud.base.remote.dto.timed.GroupTimedInterfaceInfoDto;
import com.panshicloud.base.remote.dto.timed.QrtzDto;
import com.panshicloud.base.remote.dto.timed.TimedTaskDto;
import com.panshicloud.base.remote.service.ITimedTaskManageService;
import com.panshicloud.base.remote.service.ITimedTaskRecordService;
import com.panshicloud.base.service.constants.CommonCst;
import com.panshicloud.base.service.provider.remote.SystemConfigRemote;
import com.panshicloud.base.service.util.RedisLockUtils;
import com.panshicloud.base.service.util.TimedUtils;
import com.panshicloud.base.timed.abs.AbstractGroup;
import com.panshicloud.common.base.PageDto;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.service.GenericService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author shimengmeng
 * @since 2024/4/29
 */
@DubboService
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TimedTaskManageServiceProvider extends ServiceImpl<TimedTaskMapper, TimedTask> implements ITimedTaskManageService {

    @Autowired
    private TimedTaskMapper timedTaskMapper;
    @Autowired
    private SystemConfigRemote systemConfigRemote;
    @Autowired
    private ITimedTaskRecordService timedTaskRecordService;
    @Autowired
    private TimedConfig timedConfig;
    @Autowired
    private RedisLockUtils redisLockUtils;

    /**
     * 注入任务调度
     */
    @Resource
    private Scheduler scheduler;

    @Override
    public void insert(String code, String name, String type, String groupType, String status, String operateUserName, String cornExpression, String remark, String configParams) {
        TimedTask timedTask = new TimedTask();
        List<TimedTaskDto> codeList = findByCode(code);
        if (codeList.size() >= 1) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "定时任务编码" + code + "已存在");
        }
        timedTask.setCode(code);
        List<TimedTaskDto> nameList = findByName(name);
        if (nameList.size() >= 1) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "定时任务名称" + name + "已存在");
        }
        List<GroupTimed> groupList = timedConfig.getGroupList();
        Map<String, String> groupMap = groupList.stream().collect(Collectors.toMap(item -> item.getCode(), item -> item.getInterfaceName()));
        if (!groupMap.containsKey(groupType)) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未查询到对应服务的方法信息！");
        }
        timedTask.setName(name);
        timedTask.setType(type);
        timedTask.setGroupType(groupType);
        timedTask.setStatus(status);
        timedTask.setOperateUserName(operateUserName);
        timedTask.setCornExpression(cornExpression);
        timedTask.setRemark(remark);
        timedTask.setConfigParams(configParams);
        timedTaskMapper.insert(timedTask);
        QrtzDto qrtzTask = createQrtzTaskDto(type, groupType, timedTask, groupMap);
        TimedUtils.createScheduleJob(scheduler, qrtzTask);
        if (qrtzTask.getStatus().equals(TimedTaskCst.TASK_JOB_STOP)) {
            TimedUtils.pauseScheduleJob(scheduler, qrtzTask.getGroupName(), qrtzTask.getJobName());
        }
    }

    @Override
    public PageDto<TimedTaskDto> find(String groupType, String code, String name, String type, String status, Integer pageNumber, Integer pageSize) {
        LambdaQueryWrapper<TimedTask> wrapper = new LambdaQueryWrapper<>();
        if (pageNumber == null) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "页数不能为空");
        }
        if (pageSize == null) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "每页条数不能为空");
        }
        if (code != null && StringUtils.isNotBlank(code)) {
            wrapper.like(TimedTask::getCode, code);
        }
        if (name != null && StringUtils.isNotBlank(name)) {
            wrapper.like(TimedTask::getName, name);
        }
        if (type != null && StringUtils.isNotBlank(type)) {
            wrapper.eq(TimedTask::getType, type);
        }
        if (groupType != null && StringUtils.isNotBlank(groupType)) {
            wrapper.eq(TimedTask::getGroupType, groupType);
        }
        if (status != null && StringUtils.isNotBlank(status)) {
            wrapper.eq(TimedTask::getStatus, status);
        }
        wrapper.orderByDesc(TimedTask::getStatus, TimedTask::getCreateTime);
        IPage<TimedTask> page = new Page<>(pageNumber, pageSize);
        page = timedTaskMapper.selectPage(page, wrapper);
        PageDto<TimedTaskDto> timedTaskDtoPageDto = new PageDto<>(page, TimedTaskDto.class);
        timedTaskDtoPageDto.getList().forEach(item -> {
            AbstractGroup group = this.getGroup(item.getGroupType());
            item.setGroupName(group.name());
            Map<String, String> typeMap = group.timedTaskInfos().stream().collect(Collectors.toMap(timedTaskInfo -> timedTaskInfo.getCode(), timedTaskInfo -> timedTaskInfo.getName()));
            item.setTypeName(typeMap.get(item.getType()));
        });
        return timedTaskDtoPageDto;
    }

    @Override
    public void update(String code, String name, String type, String groupType, String status, String operateUserName, String cornExpression, String remark, String id, String configParams) {
        TimedTask timedTask = new TimedTask();
        List<TimedTaskDto> codeList = findByCode(code);
        codeList.removeIf(item -> item.getId().equals(id));
        if (codeList.size() >= 1) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "定时任务编码" + code + "已存在");
        }
        timedTask.setCode(code);
        List<TimedTaskDto> nameList = findByName(name);
        nameList.removeIf(item -> item.getId().equals(id));
        if (nameList.size() >= 1) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "定时任务名称" + name + "已存在");
        }
        List<GroupTimed> groupList = timedConfig.getGroupList();
        Map<String, String> groupMap = groupList.stream().collect(Collectors.toMap(item -> item.getCode(), item -> item.getInterfaceName()));
        if (!groupMap.containsKey(groupType)) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未查询到对应服务的方法信息！");
        }
        timedTask.setId(id);
        timedTask.setName(name);
        timedTask.setType(type);
        timedTask.setGroupType(groupType);
        timedTask.setStatus(status);
        timedTask.setOperateUserName(operateUserName);
        timedTask.setCornExpression(cornExpression);
        timedTask.setRemark(remark);
        timedTask.setConfigParams(configParams);
        LambdaUpdateWrapper<TimedTask> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TimedTask::getId, id);
        update(timedTask, updateWrapper);
        QrtzDto qrtzTask = createQrtzTaskDto(type, groupType, timedTask, groupMap);
        TimedUtils.updateScheduleJob(scheduler, qrtzTask);
        if (qrtzTask.getStatus().equals(TimedTaskCst.TASK_JOB_STOP)) {
            TimedUtils.pauseScheduleJob(scheduler, qrtzTask.getGroupName(), qrtzTask.getJobName());
        }
    }

    @Override
    public TimedTaskDto getById(String id) {
        TimedTask timedTask = lambdaQuery().eq(TimedTask::getId, id).list().get(0);
        return ConvertHelper.tToV(timedTask, TimedTaskDto.class);
    }

    @Override
    public void delete(String id) {
        TimedTaskDto taskDto = getById(id);
        TimedUtils.deleteScheduleJob(scheduler, taskDto.getGroupType(), taskDto.getCode());
        LambdaQueryWrapper<TimedTask> wrapper = new LambdaQueryWrapper();
        wrapper.eq(TimedTask::getId, id);
        remove(wrapper);
        timedTaskRecordService.delete(id);
    }

    @Override
    public List<GroupTimedDefineDto> findGroupDefine() {
        return ConvertHelper.tToV(timedConfig.getGroupList(), GroupTimedDefineDto.class);
    }

    @Override
    public List<GroupTimedInterfaceInfoDto> getGroupParameter(String code) {
        AbstractGroup group = this.getGroup(code);
        List<GroupTimedInterfaceInfoDto> groupTimedParameterDtos = new ArrayList<>();
        group.timedTaskInfos().forEach(item -> {
            GroupTimedInterfaceInfoDto groupTimedParameterDto = new GroupTimedInterfaceInfoDto();
            groupTimedParameterDto.setMethodName(item.getName());
            groupTimedParameterDto.setMethodCode(item.getCode());
            groupTimedParameterDtos.add(groupTimedParameterDto);
        });
        return groupTimedParameterDtos;
    }

    @Override
    public void startUp(String id) {
        TimedTaskDto taskDto = this.getById(id);
        TimedTask timedTask = new TimedTask();
        timedTask.setId(id);
        timedTask.setStatus("true");
        updateById(timedTask);
        TimedUtils.resumeScheduleJob(scheduler, taskDto.getGroupType(), taskDto.getCode());
    }

    @Override
    public void stop(String id) {
        TimedTaskDto taskDto = this.getById(id);
        TimedTask timedTask = new TimedTask();
        timedTask.setId(id);
        timedTask.setStatus("false");
        updateById(timedTask);
        TimedUtils.pauseScheduleJob(scheduler, taskDto.getGroupType(), taskDto.getCode());
    }

    @Override
    public void runOnce(String id) {
        TimedTaskDto taskDto = this.getById(id);
        if (!redisLockUtils.lock(taskDto.getId(), taskDto.getId(), 7, TimeUnit.DAYS)) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "当前任务正在执行！");
        }
        redisLockUtils.unlock(taskDto.getId());
        TimedUtils.runOnce(scheduler, taskDto.getGroupType(), taskDto.getCode());
    }

    @Override
    public List<Map<String, Object>> queryRunJob() {
        return TimedUtils.queryRunJob(scheduler);
    }

    @Override
    public Boolean isRunJob(String groupName, String jobName) {
        return TimedUtils.isRunJob(scheduler, groupName, jobName);
    }

    public List<TimedTaskDto> findByCode(String code) {
        List<TimedTask> list = lambdaQuery().eq(TimedTask::getCode, code).list();
        return ConvertHelper.tToV(list, TimedTaskDto.class);
    }

    public List<TimedTaskDto> findByName(String name) {
        List<TimedTask> list = lambdaQuery().eq(TimedTask::getName, name).list();
        return ConvertHelper.tToV(list, TimedTaskDto.class);
    }

    /**
     * 创建定时任务对象
     *
     * @param type      方法CODE
     * @param groupType 分组CODE
     * @param timedTask 定时任务对象
     * @param groupMap  分组信息
     */
    private QrtzDto createQrtzTaskDto(String type, String groupType, TimedTask timedTask, Map<String, String> groupMap) {
        QrtzDto timedDto = new QrtzDto();
        timedDto.setGroupName(groupType);
        timedDto.setJobName(timedTask.getCode());
        timedDto.setJobClass(TimedTaskCst.TASK_JOB_CLASS);
        if ("true".equals(timedTask.getStatus())) {
            timedDto.setStatus(TimedTaskCst.TASK_JOB_START);
        } else {
            timedDto.setStatus(TimedTaskCst.TASK_JOB_STOP);
        }
        timedDto.setCronExpression(timedTask.getCornExpression());
        timedDto.setJobParam(timedTask.getConfigParams());
        timedDto.setServiceCode(groupType);
        timedDto.setMethodCode(type);
        timedDto.setMethodName(TimedTaskCst.TIMED_TASK_INTERFACE_CALL_METHOD_NAME);
        timedDto.setMethodClassPath(groupMap.get(groupType));
        timedDto.setOperateUserName(timedTask.getOperateUserName());
        timedDto.setTaskId(timedTask.getId());
        return timedDto;
    }

    /**
     * 获取抽象分组
     *
     * @param groupCode 分组编码
     * @return AbstractGroup
     */
    public AbstractGroup getGroup(String groupCode) {
        if (StringUtils.equals(groupCode, CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP)) {
            return null;
        }
        List<GroupDto> groupDtos = ConvertHelper.tToV(timedConfig.getGroupList(), GroupDto.class);
        for (GroupDto groupDto : groupDtos) {
            if (StringUtils.equals(groupDto.getCode(), groupCode)) {
                String rst;
                // 获取一个参数
                GenericService genericService;
                try {
                    genericService = systemConfigRemote.getGenericService(groupDto.getInterfaceName(), groupDto.getCode());
                } catch (Exception e) {
                    log.info("detailMessage:" + e.getMessage());
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, groupDto.getName() + "服务未上线");
                }
                rst = (String) genericService.$invoke("getGroup", new String[]{"java.lang.String"}, new String[]{groupDto.getCode()});
                if (StringUtils.isBlank(rst)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "参数分组定义为空");
                }
                // 构建抽象分组参数对象
                return AbstractGroup.build(rst);
            }
        }
        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未查询到参数");
    }

}
