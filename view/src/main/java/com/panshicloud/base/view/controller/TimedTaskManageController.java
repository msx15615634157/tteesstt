package com.panshicloud.base.view.controller;

import com.panshicloud.base.operationlog.OperationLog;
import com.panshicloud.base.remote.dto.timed.GroupTimedDefineDto;
import com.panshicloud.base.remote.dto.timed.GroupTimedInterfaceInfoDto;
import com.panshicloud.base.remote.service.ITimedTaskManageService;
import com.panshicloud.base.view.vo.request.*;
import com.panshicloud.base.view.vo.response.GroupTimedDefineResponseVo;
import com.panshicloud.base.view.vo.response.GroupTimedParameterResponseVo;
import com.panshicloud.base.view.vo.response.TimedTaskResponseVo;
import com.panshicloud.common.base.request.IdRequestVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.base.response.PageResponseVo;
import com.panshicloud.common.base.response.SuccessResponseVo;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.helper.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author shimengmeng
 * @since 2024/4/29
 */
@Api(tags = "定时任务管理API")
@RestController
@RequestMapping("/timedManage")
public class TimedTaskManageController {

    @Autowired
    private ITimedTaskManageService timedTaskService;

    @ApiOperation("获取分组参数-定时任务定义")
    @PostMapping("/getGroup")
    public GenericResponseVo<List<GroupTimedParameterResponseVo>> getGroup(@RequestBody @Validated GroupCodeRequestVo vo) {
        List<GroupTimedInterfaceInfoDto> groupTimedParameterDto = timedTaskService.getGroupParameter(vo.getCode());
        return new GenericResponseVo<>(ConvertHelper.tToV(groupTimedParameterDto, GroupTimedParameterResponseVo.class));
    }

    @ApiOperation("获取分组定义")
    @PostMapping("/findGroupDefine")
    public GenericResponseVo<List<GroupTimedDefineResponseVo>> findGroupDefine() {
        List<GroupTimedDefineDto> groupTimedDto = timedTaskService.findGroupDefine();
        return new GenericResponseVo<>(ConvertHelper.tToV(groupTimedDto, GroupTimedDefineResponseVo.class));
    }

    @ApiOperation(value = "启动定时任务")
    @PostMapping("startUp")
    @OperationLog(type = "定时任务", operation = "启动定时任务", value = "'定时任务ID：'+#vo.id")
    public SuccessResponseVo startUp(@RequestBody @Validated IdRequestVo vo) {
        timedTaskService.startUp(vo.getId());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "暂停定时任务")
    @PostMapping("stop")
    @OperationLog(type = "定时任务", operation = "暂停定时任务", value = "'定时任务ID：'+#vo.id")
    public SuccessResponseVo stop(@RequestBody @Validated IdRequestVo vo) {
        timedTaskService.stop(vo.getId());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "立即运行一次定时任务")
    @PostMapping("runOnce")
    @OperationLog(type = "定时任务", operation = "手动执行定时任务", value = "'定时任务ID：'+#vo.id")
    public SuccessResponseVo runOnce(@RequestBody @Validated IdRequestVo vo){
        timedTaskService.runOnce(vo.getId());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "校验Cron表达式")
    @PostMapping("verifyCronExpression")
    public SuccessResponseVo verifyCronExpression(@RequestBody @Validated TimedTaskVerifyCronExpressionRequestVo vo) {
        // 校验表达式是否正确
        if (!CronExpression.isValidExpression(vo.getCron())) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "cron表达式不正确：" + vo.getCron());
        }
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "新增")
    @PostMapping("insert")
    @OperationLog(type = "定时任务", operation = "新增定时任务", value = "'编码：'+#vo.code+'、名称：'+#vo.name+'、类型：'+#vo.type+'、状态：'+#vo.status+'、执行用户：'+#vo.operateUserName+'、执行频率：'+#vo.cornExpression")
    public SuccessResponseVo insert(@RequestBody @Validated TimedTaskInsertRequestVo vo) {
        timedTaskService.insert(vo.getCode(), vo.getName(), vo.getType(),vo.getGroupType(), vo.getStatus(), vo.getOperateUserName(), vo.getCornExpression(), vo.getRemark(), vo.getConfigParams());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "查询定时任务-分页")
    @PostMapping("find")
    public GenericResponseVo<PageResponseVo<TimedTaskResponseVo>> find(@RequestBody @Validated TimedTaskFindPageRequestVo vo) {
        PageResponseVo<TimedTaskResponseVo> taskResponseVoPageResponseVo = new PageResponseVo<>(timedTaskService.find(vo.getGroupType(),vo.getCode(), vo.getName(), vo.getType(), vo.getStatus(), vo.getPageNumber(), vo.getPageSize()), TimedTaskResponseVo.class);
        List<TimedTaskResponseVo> list = taskResponseVoPageResponseVo.getList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (TimedTaskResponseVo timedTaskResponseVo : list) {
            Date date = new Date();
            String cornExpression = timedTaskResponseVo.getCornExpression();
            CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cornExpression);
            //下次执行时间
            Date cornLatestTimeDate = cronSequenceGenerator.next(date);
            String cornLatestTime = simpleDateFormat.format(cornLatestTimeDate);
            timedTaskResponseVo.setCornLatestTime(cornLatestTime);
        }
        taskResponseVoPageResponseVo.setList(list);
        return new GenericResponseVo<>(taskResponseVoPageResponseVo);
    }

    @ApiOperation(value = "查询定时任务")
    @PostMapping("getById")
    public GenericResponseVo<TimedTaskResponseVo> getById(@RequestBody @Validated IdRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(timedTaskService.getById(vo.getId()), TimedTaskResponseVo.class));
    }

    @ApiOperation(value = "修改")
    @PostMapping("update")
    @OperationLog(type = "定时任务", operation = "修改定时任务", value = "'主键：'+#vo.id+'、名称：'+#vo.name+'、类型：'+#vo.type+'、状态：'+#vo.status+'、执行用户：'+#vo.operateUserName+'、执行频率：'+#vo.cornExpression")
    public SuccessResponseVo update(@RequestBody @Validated TimedTaskUpdateRequestVo vo) {
        timedTaskService.update(vo.getCode(), vo.getName(), vo.getType(),vo.getGroupType(), vo.getStatus(), vo.getOperateUserName(), vo.getCornExpression(), vo.getRemark(), vo.getId(), vo.getConfigParams());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PostMapping("delete")
    @OperationLog(type = "定时任务", operation = "删除定时任务", value = "'主键：'+#vo.id")
    public SuccessResponseVo delete(@RequestBody @Validated IdRequestVo vo) {
        timedTaskService.delete(vo.getId());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "获取所有运行中的job")
    @PostMapping("queryRunJob")
    public GenericResponseVo<List<Map<String, Object>>> queryRunJob(){
        List<Map<String, Object>> mapList = timedTaskService.queryRunJob();
        return new GenericResponseVo<>(mapList);
    }

}
