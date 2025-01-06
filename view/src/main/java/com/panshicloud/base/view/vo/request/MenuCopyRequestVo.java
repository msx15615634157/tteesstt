package com.panshicloud.base.view.vo.request;

import com.panshicloud.base.remote.dto.MenuParameterDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单新增请求vo
 * </p>
 *
 * @author yantao
 */

@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuCopyRequestVo implements Serializable {

    @ApiModelProperty(value = "菜单id", required = true)
    @NotEmpty(message = "菜单id不能为空")
    private String menuId;

}
