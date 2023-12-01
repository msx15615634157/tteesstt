package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * sql请求vo
 * </p>
 *
 * @author xingxingfa
 */

@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class SqlRequestVo implements Serializable{
    @ApiModelProperty(value = "Sql语句", required = true)
    private String sql;
}
