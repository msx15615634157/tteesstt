package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 禁用用户同步单位权限
 * </>
 *
 * @author xuwenqiang
 * @date 2023/7/20
 */

public class SyncUnitsExclude extends AbstractParameter {
    @Override
    public String code() {
        return "FinIntegrateHebSyncUnitsExclude";
    }

    @Override
    public String name() {
        return "禁用用户同步单位权限";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_TEXT;
    }

    @Override
    public String description() {
        return "禁用用户同步单位权限 （一个或多个，逗号隔开）";
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