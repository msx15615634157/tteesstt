package com.panshicloud.base.remote.dto;

import com.panshicloud.common.base.AbstractTree;
import com.panshicloud.common.helper.ConvertHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 枚举数据Dto
 * </p>
 *
 * @author wanglibin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataOptionDto extends AbstractTree<DataOptionDto> implements Serializable {

    /**
     * 类别/领域
     */
    private String domain;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态，0：弃用，1：使用
     */
    private Integer status;

    /**
     * 上级编码
     */
    private String parentCode;

    /**
     * 递归转换类型
     *
     * @param clazz 转换后类型
     * @param <T>   泛型
     * @return
     */
    public <T extends AbstractTree<T>> T convertTo(Class<T> clazz) {
        T r = ConvertHelper.tToV(this, clazz);
        List<T> children = new ArrayList<>();
        if (getChildren() != null) {
            for (DataOptionDto dto : getChildren()) {
                children.add(dto.convertTo(clazz));
            }
        }
        r.setChildren(children);
        return r;
    }

}
