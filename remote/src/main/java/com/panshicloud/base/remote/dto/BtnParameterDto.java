package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 按钮参数dto
 * </>
 *
 * @author xuwenqiang
 * @date 2023/9/19
 */

@Data
public class BtnParameterDto implements Serializable {


    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * id
     */
    private String id;

    /**
     * 是否开启权限
     */
    private Boolean isPermission;

    /**
     * 子集元素
     */
    private List<BtnParameterDto> children;

}
