package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 菜单新增请求vo
 * </p>
 *
 * @author yantao
 */

@ApiModel
@Data
public class MenuCopyResponseVo implements Serializable {

    private String newMenuCode;

}
