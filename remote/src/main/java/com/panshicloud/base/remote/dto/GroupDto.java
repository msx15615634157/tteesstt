package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 分组Dto
 * </>
 *
 * @author xuwenqiang
 * @date 2023/6/15
 */
@Data
public class GroupDto implements Serializable {

    private String code;

    private String name;

    private String interfaceName;
}
