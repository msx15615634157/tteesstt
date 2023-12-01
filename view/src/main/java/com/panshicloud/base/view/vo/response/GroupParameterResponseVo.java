package com.panshicloud.base.view.vo.response;

import com.panshicloud.base.parameter.Option;
import com.panshicloud.base.remote.dto.GroupParameterDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuwenqinag
 * @since 2023/6/16
 */
@ApiModel("分组参数响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupParameterResponseVo implements Serializable {

    @ApiModelProperty("分组编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("页签")
    private List<GroupParameterDto.Tab> tabs;


}
