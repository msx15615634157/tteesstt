package com.panshicloud.base.remote.dto;

import com.panshicloud.base.remote.constants.CommonCst;
import com.panshicloud.common.constants.JpCommonCst;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 生成表字段Dto
 * </p>
 *
 * @author wanglibin
 */

@Data
public class GenerateTableFieldDto implements Serializable {


    /**
     * 获取主键的字段信息
     */
    public static GenerateTableFieldDto getPrimaryKey() {
        GenerateTableFieldDto dto = new GenerateTableFieldDto();
        dto.setName("id");
        dto.setIsAllowNull(JpCommonCst.NO);
        dto.setIsPrimaryKey(JpCommonCst.YES);
        dto.setIsAutoIncrement(JpCommonCst.YES);
        dto.setType(CommonCst.TABLE_FIELD_TYPE_VARCHAR);
        dto.setLength(32);
        dto.setComment("主键");
        return dto;
    }

    /**
     * 获取默认的字段信息
     */
    public static List<GenerateTableFieldDto> getDefault() {
        List<GenerateTableFieldDto> fieldList = new ArrayList<>();
        {
            GenerateTableFieldDto dto = new GenerateTableFieldDto();
            dto.setName("update_time");
            dto.setIsAllowNull(JpCommonCst.NO);
            dto.setType(CommonCst.TABLE_FIELD_TYPE_DATE);
            dto.setComment("更新时间");
            fieldList.add(dto);
        }
        {
            GenerateTableFieldDto dto = new GenerateTableFieldDto();
            dto.setName("update_user_id");
            dto.setIsAllowNull(JpCommonCst.NO);
            dto.setType(CommonCst.TABLE_FIELD_TYPE_VARCHAR);
            dto.setLength(32);
            dto.setComment("更新人");
            fieldList.add(dto);
        }
        {
            GenerateTableFieldDto dto = new GenerateTableFieldDto();
            dto.setName("create_time");
            dto.setIsAllowNull(JpCommonCst.NO);
            dto.setType(CommonCst.TABLE_FIELD_TYPE_DATE);
            dto.setComment("创建时间");
            fieldList.add(dto);
        }
        {
            GenerateTableFieldDto dto = new GenerateTableFieldDto();
            dto.setName("create_user_id");
            dto.setIsAllowNull(JpCommonCst.NO);
            dto.setType(CommonCst.TABLE_FIELD_TYPE_VARCHAR);
            dto.setComment("创建人");
            dto.setLength(32);
            fieldList.add(dto);
        }
        return fieldList;
    }

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型
     * <p>
     * 枚举类：com.jp.base.constants.CommonCst
     */
    private Integer type;

    /**
     * 字段长度
     * <p>
     * date和datetime不需要长度
     */
    private Integer length;

    /**
     * 是否允许为空
     */
    private Integer isAllowNull;

    /**
     * 是否是主键
     */
    private Integer isPrimaryKey;

    /**
     * 是否自增
     */
    private Integer isAutoIncrement;

    /**
     * 备注
     */
    private String comment;

}
