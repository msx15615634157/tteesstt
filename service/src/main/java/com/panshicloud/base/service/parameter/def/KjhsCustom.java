package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.Option;
import com.panshicloud.common.constants.JpCommonCst;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 报表使用
 * </>
 *
 * @author xuwenqiang
 * @sinse 2023/5/8
 */

public class KjhsCustom extends AbstractParameter {

    @Override
    public String code() {
        return "KjhsCustom";
    }

    @Override
    public String name() {
        return "会计核算风格";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_SWITCH;
    }

    @Override
    public String description() {
        return "是否启用会计核算风格";
    }

    @Override
    public String value() {
        return  String.valueOf(JpCommonCst.NO);
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
