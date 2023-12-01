package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.service.IOperationLogService;
import com.panshicloud.base.view.vo.request.OperationLogLikeRequestVo;
import com.panshicloud.base.view.vo.response.OperationLogResponseVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.base.response.PageResponseVo;
import com.panshicloud.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * @author xingxingfa
 * @since 2022/12/28
 */
@Api(tags = "操作日志相关API")
@RestController
@RequestMapping("/operation/log")
public class OperationLogController {

    @DubboReference
    private IOperationLogService operationLogService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("查询操作日志-分页")
    @PostMapping("/findPage")
    public GenericResponseVo<PageResponseVo<OperationLogResponseVo>> findPage(@RequestBody @Validated OperationLogLikeRequestVo vo) {
        return new GenericResponseVo<>(new PageResponseVo<>(operationLogService.findPage(vo.getPageNumber(), vo.getPageSize(), vo.getStartTime(), vo.getEndTime(), vo.getType(), vo.getOperation(), vo.getOperationUserId()), OperationLogResponseVo.class));
    }

    @ApiOperation("准备导出")
    @PostMapping("/beforeExport")
    public GenericResponseVo<String> beforeExport(@RequestBody @Validated OperationLogLikeRequestVo vo) {
        String token = StringUtils.getNumberId();
        ValueOperations<String, OperationLogLikeRequestVo> ops = redisTemplate.opsForValue();
        ops.set(token, vo, 6000, TimeUnit.SECONDS);
        return new GenericResponseVo<>(token);
    }

    @ApiOperation("归档导出")
    @GetMapping("/archiveExport")
    public GenericResponseVo<String> archiveExport(@RequestParam String token, HttpServletResponse response) {
        ValueOperations<String, OperationLogLikeRequestVo> ops = redisTemplate.opsForValue();
        OperationLogLikeRequestVo vo = ops.get(token);
        if (vo == null) {
            return new GenericResponseVo<>("归档导出失败，请重试");
        }
        //设置文件的原文件名，若文件名中含有中文则需要解码，否则会出现乱码
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("日志", "utf-8") + ".zip");
            OutputStream outputStream = response.getOutputStream();
            ByteArrayOutputStream byteArrayOutputStream = operationLogService.archiveExport(vo.getStartTime(), vo.getEndTime(), vo.getType(), vo.getOperation(), vo.getOperationUserId());
            byteArrayOutputStream.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        operationLogService.delete(vo.getStartTime(), vo.getEndTime(), vo.getType(), vo.getOperation(), vo.getOperationUserId());
        return new GenericResponseVo<>("归档导出成功");
    }
}
