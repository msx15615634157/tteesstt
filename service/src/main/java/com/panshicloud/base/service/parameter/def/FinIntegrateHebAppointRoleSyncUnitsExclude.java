package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 管理员参数
 * </>
 *
 * @author xuwenqiang
 * @date 2023/5/8
 */
public class FinIntegrateHebAppointRoleSyncUnitsExclude extends AbstractParameter {

    @Override
    public String code() {
        return "FinIntegrateHebAppointRoleSyncUnitsExclude";
    }

    @Override
    public String name() {
        return "禁用角色同步单位权限";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_TEXT;
    }

    @Override
    public String description() {
        return "禁用角色同步角色权限 用户角色编码 （一个或多个，逗号隔开）";
    }

    @Override
    public String value() {
        return "";
    }

    @Override
    public List<Option> options() {
        return new ArrayList<>();
    }

    @Override
    public Integer dataType() {
        return AbstractParameter.VALUE_TYPE_STRING;
    }

}
