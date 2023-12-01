package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 枚举新增请求vo
 * </p>
 *
 * @author huangrankun
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class DataOptionInsertRequestVo extends DataOptionIdRequestVo {

    @ApiModelProperty(value = "字典名称", required = true)
    @NotEmpty(message = "字典名称不能为空")
    private String name;

    @ApiModelProperty("描述")
    private String description;

}
