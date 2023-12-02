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

public class IsValidYsdm extends AbstractParameter{

    @Override
    public String code() {
        return "FinIntegrateHebSsoValidYsdm";
    }

    @Override
    public String name() {
        return "河北一体化单点登录-校验单位时增加预算代码匹配";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_SWITCH;
    }

    @Override
    public String description() {
        return "登录时校验本系统用户的所属单位是否与一体化一致，默认使用一体化的区划字段匹配，开启后，则使用一体化的区划+预算单位字段来匹配。建议：部门决算系统匹配区划+预算单位代码；总决算系统校验区划代码";
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
