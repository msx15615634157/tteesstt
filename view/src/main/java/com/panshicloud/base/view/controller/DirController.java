package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.dto.DirInsertDto;
import com.panshicloud.base.remote.dto.DirUpdateDto;
import com.panshicloud.base.remote.service.IDirService;
import com.panshicloud.base.view.vo.request.*;
import com.panshicloud.base.view.vo.response.DirResponseVo;
import com.panshicloud.base.view.vo.response.DirTreeResponseVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.base.response.SuccessResponseVo;
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
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/27 11:39
 */
@Api(tags = "目录")
@RestController
@RequestMapping("/dir")
public class DirController {

    @DubboReference
    private IDirService dirService;

    @ApiOperation("新增")
    @PostMapping("/insert")
    public SuccessResponseVo insert(@RequestBody @Validated DirInsertRequestVo vo) {
        dirService.insert(ConvertHelper.tToV(vo, DirInsertDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("修改目录名称")
    @PostMapping("/update")
    public SuccessResponseVo update(@RequestBody @Validated DirUpdateRequestVo vo) {
        dirService.update(ConvertHelper.tToV(vo, DirUpdateDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("根据编码和类型删除")
    @PostMapping("/delete")
    public SuccessResponseVo delete(@RequestBody @Validated DirTypeAndCodeRequestVo vo) {
        dirService.delete(vo.getType(), vo.getCode());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("根据编码和类型获取指定目录")
    @PostMapping("/get")
    public GenericResponseVo<DirResponseVo> get(@RequestBody @Validated DirTypeAndCodeRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(dirService.get(vo.getType(), vo.getCode()), DirResponseVo.class));
    }

    @ApiOperation("根据类型获取全部目录")
    @PostMapping("/find")
    public GenericResponseVo<List<DirResponseVo>> find(@RequestBody @Validated DirTypeRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(dirService.find(vo.getType()), DirResponseVo.class));
    }

    @ApiOperation("根据类型获取全部目录并转换成树形")
    @PostMapping("/findToTree")
    public GenericResponseVo<List<DirTreeResponseVo>> findToTree(@RequestBody @Validated DirTypeRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(dirService.findToTree(vo.getType()), DirTreeResponseVo.class));
    }

    @ApiOperation("更新目录排序")
    @PostMapping("/updateSort")
    public SuccessResponseVo updateSort(@RequestBody @Validated DirUpdateSortRequestVo vo) {
        dirService.updateSort(vo.getType(), vo.getCode(), vo.getParentCode(), vo.getSort());
        return ResponseHelper.SUCCESS;
    }

}
