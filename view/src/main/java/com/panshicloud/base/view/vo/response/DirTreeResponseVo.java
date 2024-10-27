package com.panshicloud.base.view.vo.response;

import com.panshicloud.common.base.AbstractTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/29 10:29
 */

@ApiModel("目录响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class DirTreeResponseVo extends AbstractTree<DirTreeResponseVo> implements Serializable {

    @ApiModelProperty("目录编码")
    private String code;

    @ApiModelProperty("目录名称")
    private String name;

    @ApiModelProperty("目录类型")
    private String type;

    @ApiModelProperty("上级编码")
    private String parentCode;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
