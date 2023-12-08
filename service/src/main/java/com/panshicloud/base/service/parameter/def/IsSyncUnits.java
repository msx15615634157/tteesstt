package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.Option;
import com.panshicloud.common.constants.JpCommonCst;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 是否同步单位权限开关
 * </>
 *
 * @author xuwenqiang
 * @date 2023/7/20
 */

public class IsSyncUnits extends AbstractParameter {
    @Override
    public String code() {
        return "FinIntegrateHebIsSyncUnits";
    }

    @Override
    public String name() {
        return "河北一体化权限同步-单位权限";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_SWITCH;
    }

    @Override
    public String description() {
        return "同步用户的单位权限";
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
