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
public class MenuUpdateSortRequestVo {

    @ApiModelProperty(value = "id", required = true)
    @NotEmpty(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "父级菜单", required = true)
    private String parentId;

    @ApiModelProperty(value = "权重值", required = true)
    @NotNull(message = "权重值不能为空")
    private Integer sort;

}
