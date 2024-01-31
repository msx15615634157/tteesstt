package com.panshicloud.base.operationlog;

import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogService;
import com.panshicloud.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * <p>
 * 操作日志切面
 * </p>
 *
 * @author huangrankun
 */

@Aspect
@Order(0)
@Component
@Slf4j
public class OperationLogAspect {

    @DubboReference
    private IOperationLogService operationLogService;

    @Autowired(required = false)
    private HttpServletRequest request;

    /**
     * 用于SpEL表达式解析.
     */
    private static SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private static DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation(com.panshicloud.base.operationlog.OperationLog)")
    private void pointcutInterface() {
    }

    @Around("pointcutInterface()")
    public Object exec(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = null;
        String operation = null;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Class<?> clazz = joinPoint.getTarget().getClass();
            Method method = signature.getMethod();
            Object[] args = joinPoint.getArgs();
            OperationLog operationLog = method.getAnnotation(OperationLog.class);
            // 获取类型
            type = operationLog.type();
            Api api = clazz.getAnnotation(Api.class);
            if (StringUtils.isBlank(type)) {
                if (api != null) {
                    String[] tags = api.tags();
                    if (tags.length > 0) {
                        type = tags[0];
                    }
                }
                // 依然为空，则取类名
                if (StringUtils.isBlank(type)) {
                    type = clazz.getName();
                }
            }
            // 获取操作
            operation = operationLog.operation();
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (StringUtils.isBlank(operation)) {
                if (apiOperation != null) {
                    operation = apiOperation.value();
                }
                // 依然为空，则取方法名
                if (StringUtils.isBlank(operation)) {
                    operation = method.getName();
                }
            }
            // 获取info
            String info = operationLog.value();
            if (StringUtils.isBlank(info)) {
                info = String.format("%s - %s", type, operation);
            } else {
                String[] paramNames = nameDiscoverer.getParameterNames(method);
                if (paramNames != null) {
                    Expression expression = parser.parseExpression(info);
                    EvaluationContext context = new StandardEvaluationContext();
                    for (int i = 0; i < args.length; i++) {
                        context.setVariable(paramNames[i], args[i]);
                    }
                    info = expression.getValue(context).toString();
                }
            }
            // 保存日志
            OperationLogDto entity = new OperationLogDto();
            entity.setType(type);
            entity.setOperation(operation);
            entity.setLogInfo(info);
            entity.setInterfaceName(clazz.getName());
            entity.setServerIp(this.request.getLocalAddr());
            String xForwardedFor = this.request.getHeader("X-Forwarded-For");
            entity.setClientIp(StringUtils.isBlank(xForwardedFor) ? xForwardedFor : StringUtils.split(this.request.getHeader("X-Forwarded-For"))[0]);
            entity.setClientCode(this.request.getHeader("User-Agent"));
            entity.setUri(this.request.getRequestURI());
            entity.setUrl(this.request.getRequestURL().toString());
            entity.setToken(this.request.getHeader("Access-Token-User"));
            entity.setQueryString(this.request.getQueryString());
            operationLogService.insert(entity);
        } catch (Exception e) {
            log.error("保存操作日志异常，异常位置：" + type + "模块的" + operation + "操作");
            e.printStackTrace();
        }
        return joinPoint.proceed();
    }

}
