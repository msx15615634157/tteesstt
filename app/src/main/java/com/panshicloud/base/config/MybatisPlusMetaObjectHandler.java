package com.panshicloud.base.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.panshicloud.common.constants.JpCommonCst;
import com.panshicloud.common.context.CommonContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @Author: wanglibin{
 * setFieldValByName("isDelete", JpCommonCst.NO, meta);
 * }
 * @Description: mybitas plus meta object handler
 * @Date: 2021/7/1 12:25
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject meta) {
        if (meta.hasSetter("status") && meta.getValue("status") == null) {
            setFieldValByName("status", 0, meta);
        }
        if (meta.hasSetter("isUse") && meta.getValue("isUse") == null) {
            setFieldValByName("isUse", JpCommonCst.YES, meta);
        }
        if (meta.hasSetter("isDelete") && meta.getValue("isDelete") == null) {
            setFieldValByName("isDelete", JpCommonCst.NO, meta);
        }
        if (meta.hasSetter("createTime")) {
            setFieldValByName("createTime", new Date(), meta);
        }
        if (meta.hasSetter("updateTime")) {
            setFieldValByName("updateTime", new Date(), meta);
        }
        if (meta.hasSetter("operationTime")) {
            setFieldValByName("operationTime", new Date(), meta);
        }
        if (meta.hasSetter("createUserId")) {
            setFieldValByName("createUserId", this.getUserId(), meta);
        }
        if (meta.hasSetter("updateUserId")) {
            setFieldValByName("updateUserId", this.getUserId(), meta);
        }
        if (meta.hasSetter("operationUserId") && meta.getValue("operationUserId") == null) {
            setFieldValByName("operationUserId", this.getUserId(), meta);
        }
        if (meta.hasSetter("operationOrganizationId") && meta.getValue("operationOrganizationId") == null) {
            setFieldValByName("operationOrganizationId", getOrganizationId(), meta);
        }
    }

    @Override
    public void updateFill(MetaObject meta) {
        if (meta.hasSetter("updateTime")) {
            setFieldValByName("updateTime", new Date(), meta);
        }
        if (meta.hasSetter("updateUserId")) {
            setFieldValByName("updateUserId", this.getUserId(), meta);
        }
    }

    private String getUserId() {
        return CommonContext.getUser() == null ? "NO LOGIN" : CommonContext.getUser().getId();
    }

    private String getOrganizationId() {
        return CommonContext.getUser() == null ? "NO LOGIN" : CommonContext.getUser().getOrganizationId();
    }

}
