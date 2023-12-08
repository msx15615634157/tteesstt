package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 菜单请求vo
 * </p>
 *
 * @author yantao
 */

@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuFindRequestVo {

    @ApiModelProperty(value = "所属应用", required = true)
    @NotNull(message = "所属应用不能为空")
    private String appId;

}
