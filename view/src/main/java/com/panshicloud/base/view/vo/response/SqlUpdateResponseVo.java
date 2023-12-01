package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 增删改sql响应对象
 * </p>
 *
 * @author xingxingfa
 */

@ApiModel("增删改sql响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class SqlUpdateResponseVo implements Serializable {

    @ApiModelProperty("正确信息")
    String correct;

    @ApiModelProperty("错误信息")
    String error;
}
