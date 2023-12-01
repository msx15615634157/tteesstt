package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * CODE请求对象
 * </>
 *
 * @author xuwenqiang
 * @date 2023/7/27
 */

@ApiModel("CODE请求对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupCodeRequestVo implements Serializable {

    @ApiModelProperty(value = "code", required = true)
    private String code;
}