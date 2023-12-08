package com.panshicloud.base.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panshicloud.base.dao.dos.DataOptionCountDo;
import com.panshicloud.base.dao.entity.DataOption;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author wanglibin
 * @since 2021-09-09
 */
@Component
public interface DataOptionMapper extends BaseMapper<DataOption> {

    /**
     * 获取
     *
     * @param domain domain
     * @param code   编码
     * @return DataOption
     */
    @Select("select * from jp_data_option where domain = #{domain} and code = #{code}")
    DataOption get(String domain, String code);

    /**
     * 删除
     *
     * @param domain domain
     * @param code   编码
     */
    @Update("delete from jp_data_option where domain = #{domain} and code = #{code}")
    void delete(String domain, String code);

    /**
     * 更新
     *
     * @param domain      domain
     * @param code        编码
     * @param name        名称
     * @param description 描述
     */
    @Update("update jp_data_option set name = #{name}, description = #{description} where domain = #{domain} and code = #{code}")
    void update(String domain, String code, String name, String description);

    /**
     * 更新状态
     *
     * @param domain domain
     * @param code   编码
     * @param status 状态
     */
    @Update("update jp_data_option set status = #{status} where domain = #{domain} and code = #{code}")
    void updateStatus(String domain, String code, Integer status);

    /**
     * 通过domain计数
     *
     * @return List<DataOptionCountDo>
     */
    @Select("select domain, count(1) as number from jp_data_option group by domain")
    List<DataOptionCountDo> countByDomain();

}
