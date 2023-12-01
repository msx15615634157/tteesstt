package com.panshicloud.base.service.parameter.def;

import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.AbstractTab;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 默认页签
 * </>
 *
 * @author xuwenqiang
 * @date 2023/6/15
 */

public class DefaultTab extends AbstractTab {

    @Override
    public String name() {
        return "页签参数";
    }

    @Override
    public List<AbstractParameter> parameters() {
        List<AbstractParameter> rst = new ArrayList<>();
        rst.add(new IsSyncRoles());
        rst.add(new IsSyncUnits());
        rst.add(new SyncRolesExclude());
        rst.add(new SyncUnitsExclude());
        rst.add(new SysCustomSummaryTargetName());
        rst.add(new KjhsCustom());
        return rst;
    }


}
