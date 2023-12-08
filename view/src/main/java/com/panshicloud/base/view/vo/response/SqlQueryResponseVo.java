package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 查询sql响应对象
 * </p>
 *
 * @author xingxingfa
 */

@ApiModel("查询sql响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class SqlQueryResponseVo implements Serializable {

    @ApiModelProperty("头部字段名")
    List<String> head;

    @ApiModelProperty("字段对应数据")
    List<Map<String, String>> data;

    @ApiModelProperty("错误信息")
    String error;
}
