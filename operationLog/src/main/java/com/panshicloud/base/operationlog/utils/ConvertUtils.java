package com.panshicloud.base.operationlog.utils;

import com.panshicloud.base.remote.dto.OperationLogDto;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xingxingfa
 * @since 2022/12/27
 */
public class ConvertUtils {

    @Deprecated
    public static OperationLogDto getOperationLogDto(HttpServletRequest request, String type, String operation, String logInfo, String interfaceName) {
        // 保存日志
        OperationLogDto entity = new OperationLogDto();
        entity.setType(type);
        entity.setOperation(operation);
        entity.setLogInfo(logInfo);
        entity.setInterfaceName(interfaceName);
        setRequest(entity, request);
        return entity;
    }

    public static OperationLogDto getOperation(Class<?> clazz, String type, String operation, String info) {
        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return getOperation(request, clazz, type, operation, info);
    }

    private static OperationLogDto getOperation(HttpServletRequest request, Class<?> clazz, String type, String operation, String info){
        // 保存日志
        OperationLogDto entity = new OperationLogDto();
        entity.setType(type);
        entity.setOperation(operation);
        entity.setLogInfo(info);
        entity.setInterfaceName(clazz.getName());
        setRequest(entity, request);
        return entity;
    }

    private static void setRequest(OperationLogDto entity, HttpServletRequest request){
        entity.setServerIp(request.getLocalAddr());
        entity.setClientIp(request.getRemoteAddr());
        entity.setClientCode(request.getHeader("User-Agent"));
        entity.setUri(request.getRequestURI());
        entity.setUrl(request.getRequestURL().toString());
        entity.setToken(request.getHeader("Access-Token-User"));
        entity.setQueryString(request.getQueryString());
    }

}
