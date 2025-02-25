package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author yantao
 * @since 2023/12/5
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("psc_query_index_temp")
public class QueryIndexTemp {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 查询id
     */
    private String queryId;

    /**
     * 排序
     */
    private Integer sort;
    /**
     * md5
     */
    private String md5;

}
