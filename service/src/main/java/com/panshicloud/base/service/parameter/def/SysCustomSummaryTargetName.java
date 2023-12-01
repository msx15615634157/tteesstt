package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 汇总指标名称
 * </>
 *
 * @author xuwenqiang
 * @date 2023/7/20
 */

public class SysCustomSummaryTargetName extends AbstractParameter {
    @Override
    public String code() {
        return "SysCustomSummaryTargetName";
    }

    @Override
    public String name() {
        return "汇总指标名称";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_TEXT;
    }

    @Override
    public String description() {
        return "汇总指标自定义名字";
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
