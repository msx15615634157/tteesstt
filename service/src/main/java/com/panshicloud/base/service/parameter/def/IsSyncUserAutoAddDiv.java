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

public class IsSyncUserAutoAddDiv extends AbstractParameter{

    @Override
    public String code() {
        return "FinIntegrateHebSyncUserAutoAddDiv";
    }

    @Override
    public String name() {
        return "河北一体化用户同步-用户账号添加区划前缀";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_SWITCH;
    }

    @Override
    public String description() {
        return "开启后，一体化同步用户账号时，添加区划代码作为用户名的前缀，适用于存在不同区划下相同账号名的情况";
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
