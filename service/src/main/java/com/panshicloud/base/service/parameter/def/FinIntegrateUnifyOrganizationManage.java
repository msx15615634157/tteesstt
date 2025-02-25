package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.Option;
import com.panshicloud.common.constants.JpCommonCst;

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
public class FinIntegrateUnifyOrganizationManage extends AbstractParameter {

    @Override
    public String code() {
        return "FinIntegrateUnifyOrganizationManage";
    }

    @Override
    public String name() {
        return "是否开启统一单位管理";
    }

    @Override
    public Integer type() {
        return AbstractParameter.TYPE_SWITCH;
    }

    @Override
    public String description() {
        return "开启后在各模块切换时统一单位展示";
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
