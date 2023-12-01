package com.panshicloud.base.config;

import com.alibaba.fastjson.JSONObject;
import com.panshicloud.common.base.response.ErrorHasDataResponseVo;
import com.panshicloud.common.base.response.ErrorResponseVo;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wanglibin
 * @Description: exception control
 * @Date: 2021/6/30 11:50
 * @Version: 1.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ErrorResponseVo errorHandler(MethodArgumentNotValidException e) {
        List<String> result = e.getBindingResult().getFieldErrors().stream().map(it -> it.getDefaultMessage()).collect(Collectors.toList());
        log.error("参数错误: {}", result);
        return new ErrorHasDataResponseVo(JpErrorCodeCst.PARAMETER_VALIDATE_ERROR, result.get(0), result);
    }

    @ResponseBody
    @ExceptionHandler(value = {BizRuntimeException.class})
    public ErrorResponseVo errorHandler(BizRuntimeException e) {
        log.error("系统异常: {}（{}）", e.getMessage(), e.getErrorCode());
        return new ErrorResponseVo(e.getErrorCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {RpcException.class})
    public ErrorResponseVo errorHandler(RpcException e) {
        log.error("系统异常: {}", e.getMessage());
        return JSONObject.parseObject(e.getMessage(), ErrorResponseVo.class);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ErrorResponseVo errorHandler(Exception e) {
        log.error("未知异常", e);
        return new ErrorResponseVo(JpErrorCodeCst.SYSTEM_ERROR, "哎呀呀~一不小心出错了。");
    }

}
