package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.dto.QueryDto;
import com.panshicloud.base.remote.dto.UpdateDto;
import com.panshicloud.base.remote.service.ISqlExecutionService;
import com.panshicloud.base.view.vo.request.SqlRequestVo;
import com.panshicloud.base.view.vo.response.SqlQueryResponseVo;
import com.panshicloud.base.view.vo.response.SqlUpdateResponseVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.helper.ConvertHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xingxingfa
 * @BelongProject: base
 * @BelongPackage: com.panshicloud.base.view.controller
 * @CreateTime: 2022/11/17
 * @Description: Sql执行
 */
@Api(tags = "Sql执行")
@RestController
@RequestMapping("/sql")
public class SqlExecutionController {
    @DubboReference
    private ISqlExecutionService sqlExecutionService;

    @ApiOperation("执行查询sql并返回操作结果")
    @PostMapping("/getQuerySql")
    public GenericResponseVo<SqlQueryResponseVo> getQuerySql(@RequestBody @Validated SqlRequestVo vo) {
        QueryDto querySql = sqlExecutionService.getQuerySql(vo.getSql());
        if (querySql.getData().size() > 2000) {
            SqlQueryResponseVo sqlQueryResponseVo = new SqlQueryResponseVo();
            sqlQueryResponseVo.setError("查询数据量超过2000条，请用分页语句查询");
            return new GenericResponseVo<>(sqlQueryResponseVo);
        }
        return new GenericResponseVo<>(ConvertHelper.tToV(querySql, SqlQueryResponseVo.class));
    }

    @ApiOperation("执行修改sql并返回操作结果")
    @PostMapping("/getUpdateSql")
    public GenericResponseVo<SqlUpdateResponseVo> getUpdateSql(@RequestBody @Validated SqlRequestVo vo) {
        UpdateDto updateDto = sqlExecutionService.getUpdateSql(vo.getSql());
        return new GenericResponseVo<>(ConvertHelper.tToV(updateDto, SqlUpdateResponseVo.class));
    }

}
