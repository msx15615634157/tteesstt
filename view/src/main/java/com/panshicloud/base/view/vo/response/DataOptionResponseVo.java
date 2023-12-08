package com.panshicloud.base.view.vo.response;

import com.panshicloud.common.base.AbstractTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 枚举请求vo
 * </p>
 *
 * @author huangrankun
 */
@ApiModel
@Data
public class DataOptionResponseVo extends AbstractTree<DataOptionResponseVo> implements Serializable {

    @ApiModelProperty("类别/领域")
    private String domain;

    @ApiModelProperty("字典编码")
    private String code;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

}
