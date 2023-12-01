package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.service.IDataOptionService;
import com.panshicloud.base.view.vo.request.DataOptionIdRequestVo;
import com.panshicloud.base.view.vo.request.DataOptionInsertRequestVo;
import com.panshicloud.base.view.vo.request.DataOptionUpdateRequestVo;
import com.panshicloud.base.view.vo.response.DataOptionResponseVo;
import com.panshicloud.common.base.request.CodeRequestVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.base.response.SuccessResponseVo;
import com.panshicloud.common.constants.JpCommonCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangrankun
 */

@Api(tags = "数据字典相关API")
@RestController
@RequestMapping("/dataOption")
public class DataOptionController {

    @DubboReference
    private IDataOptionService dataOptionService;

    @ApiOperation(value = "新增")
    @PostMapping("insert")
    public SuccessResponseVo insert(@RequestBody @Validated DataOptionInsertRequestVo requestVo) {
        dataOptionService.insert(
                requestVo.getDomain(),
                requestVo.getCode(),
                requestVo.getName(),
                requestVo.getDescription()
        );
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public SuccessResponseVo delete(@RequestBody @Validated DataOptionIdRequestVo requestVo) {
        dataOptionService.delete(
                requestVo.getDomain(),
                requestVo.getCode()
        );
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PostMapping("update")
    public SuccessResponseVo update(@RequestBody @Validated DataOptionUpdateRequestVo requestVo) {
        dataOptionService.update(
                requestVo.getDomain(),
                requestVo.getCode(),
                requestVo.getName(),
                requestVo.getDescription()
        );
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "更新为使用状态")
    @PostMapping("update/toUse")
    public SuccessResponseVo updateToUse(@RequestBody @Validated DataOptionIdRequestVo requestVo) {
        dataOptionService.updateStatus(
                requestVo.getDomain(),
                requestVo.getCode(),
                JpCommonCst.YES
        );
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "更新为未使用状态")
    @PostMapping("update/toUnUse")
    public SuccessResponseVo updateToUnUse(@RequestBody @Validated DataOptionIdRequestVo requestVo) {
        dataOptionService.updateStatus(
                requestVo.getDomain(),
                requestVo.getCode(),
                JpCommonCst.NO
        );
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "查询树形结构")
    @PostMapping("findTree")
    public GenericResponseVo<List<DataOptionResponseVo>> findTree(@RequestBody @Validated CodeRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(dataOptionService.findByDomainToTree(vo.getCode(), null), DataOptionResponseVo.class));
    }

}
