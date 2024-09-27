package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.service.ITimedTaskRecordService;
import com.panshicloud.base.view.vo.request.TimedTaskRecordRequestVo;
import com.panshicloud.base.view.vo.response.TimedTaskRecordResponseVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.base.response.PageResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务
 *
 * @author shimengmeng
 * @since 2024/4/30
 */
@Api(tags = "定时任务执行记录")
@RestController
@RequestMapping("/timedTaskRecord")
public class TimedTaskRecordController {

    @Autowired
    private ITimedTaskRecordService timedTaskRecordService;

    @ApiOperation(value = "查询定时任务执行记录-分页")
    @PostMapping("find")
    public GenericResponseVo<PageResponseVo<TimedTaskRecordResponseVo>> find(@RequestBody @Validated TimedTaskRecordRequestVo vo){
        return new GenericResponseVo<>(new PageResponseVo<>(timedTaskRecordService.findByTask(vo.getTaskId(), vo.getStartTime(), vo.getEndTime(), vo.getStatus(), vo.getPageNumber(), vo.getPageSize()), TimedTaskRecordResponseVo.class));
    }
}
