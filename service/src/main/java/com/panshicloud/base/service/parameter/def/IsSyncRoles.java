package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.Option;
import com.panshicloud.common.constants.JpCommonCst;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 是否同步用户权限开关
 * </>
 *
 * @author xuwenqiang
 * @date 2023/7/20
 */

public class IsSyncRoles extends AbstractParameter{

    @Override
    public String code() {
        return "FinIntegrateHebIsSyncRoles";
    }

    @Override
    public String name() {
        return "是否同步用户权限开关";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_SWITCH;
    }

    @Override
    public String description() {
        return "是否同步用户权限";
    }

    @Override
    public String value() {
        return String.valueOf(JpCommonCst.YES);
    }

    @Override
    public List<Option> options() {
        return new ArrayList<>();
    }

    @Override
    public Integer dataType() {
        return AbstractParameter.VALUE_TYPE_INT;
    }
}
