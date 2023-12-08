package com.panshicloud.base.view.vo.request;

import com.panshicloud.base.remote.dto.MenuParameterDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 菜单更新请求vo
 * </p>
 *
 * @author yantao
 */

@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuGetRequestVo {

    @ApiModelProperty(value = "应用编码", required = true)
    @NotEmpty(message = "应用编码不能为空")
    private String appCode;

    @ApiModelProperty(value = "菜单编码", required = true)
    @NotEmpty(message = "菜单编码不能为空")
    private String code;

}
