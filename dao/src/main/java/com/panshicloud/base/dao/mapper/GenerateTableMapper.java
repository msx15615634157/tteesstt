package com.panshicloud.base.dao.mapper;

import com.panshicloud.base.dao.dos.FieldTypeDo;
import com.panshicloud.base.dao.dos.GenerateTableFieldDo;
import com.panshicloud.base.dao.dos.FieldCommentDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 生成表 Mapper
 * </p>
 *
 * @author wanglibin
 */

@Component
public interface GenerateTableMapper {

    /**
     * 表是否存在
     *
     * @param tableName 表名
     * @return 是否存在
     */
    int isExist(@Param("tableName") String tableName);

    /**
     * 表字段是否存在
     *
     * @param tableName  表名
     * @param field      表字段
     * @param tableSpace 表空间
     * @return
     */
    int isFieldExist(String tableName, String field, String tableSpace);

    /**
     * 创建表
     *
     * @param tableName 表名
     * @param list      字段列表
     */
    void generate(@Param("tableName") String tableName, @Param("list") List<GenerateTableFieldDo> list);

    /**
     * 根据建表语句创建表
     *
     * @param sql sql
     */
    void createTable(String sql);

    /**
     * 生成
     *
     * @param tableName 表名
     * @param field     字段名
     * @param comment   表字段注释
     */
    void generateComment(@Param("tableName") String tableName, @Param("field") String field, @Param("comment") String comment);

    /**
     * 删除表
     * 物理删除
     *
     * @param tableName 表名
     */
    void delete(String tableName);

    /**
     * 重命名
     *
     * @param tableName   表名
     * @param toTableName 修改后表名
     */
    void rename(@Param("tableName") String tableName, @Param("toTableName") String toTableName);

    /**
     * 创建索引
     *
     * @param tableName  表名
     * @param indexName  索引名
     * @param columnList 索引字段
     * @param indexType  索引类型  不填时-普通索引  1-唯一索引
     */
    void createIndex(@Param("tableName") String tableName, @Param("indexName") String indexName, @Param("columnList") String columnList, @Param("indexType") String indexType);

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     */
    void deleteIndex(@Param("indexName") String indexName);

    /**
     * 按表查索引
     *
     * @param tableName 表名
     * @return 索引
     */
    List<String> findIndex(@Param("tableName") String tableName);

    /**
     * 新增字段
     *
     * @param tableName 表名称
     * @param field     字段
     */
    void insertField(@Param("tableName") String tableName, @Param("field") GenerateTableFieldDo field);

    /**
     * 批量新增字段
     *
     * @param tableName 表名称
     * @param field     字段
     */
    void batchInsertField(@Param("tableName") String tableName, @Param("list") List<GenerateTableFieldDo> field);

    /**
     * 删除字段
     *
     * @param tableName 表名称
     * @param fieldName 字段名称
     */
    void deleteField(@Param("tableName") String tableName, @Param("fieldName") String fieldName);

    /**
     * 批量删除字段
     *
     * @param tableName     表名称
     * @param fieldNameList 字段名称列表
     */
    void batchDeleteField(@Param("tableName") String tableName, @Param("list") List<String> fieldNameList);

    /**
     * 删除字段
     *
     * @param tableName   表名称
     * @param fieldName   字段名称
     * @param toFieldName 修改后字段名称
     */
    void renameField(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("toFieldName") String toFieldName);

    /**
     * 查指定表空间的所有表
     *
     * @param tableSpace 表空间
     * @return
     */
    List<Map<String, Object>> findAllTable(@Param("tableSpace") String tableSpace);

    /**
     * 查指定表的表字段以及对应的表字段注释
     *
     * @param tableName  表名
     * @param tableSpace 表空间
     * @return
     */
    List<FieldCommentDo> findFieldAndComments(@Param("tableName") String tableName, @Param("tableSpace") String tableSpace);

    /**
     * 查指定表建表语句
     *
     * @param tableName  表名
     * @param tableSpace 表空间
     * @return
     */
    String findTableStructure(@Param("tableName") String tableName, @Param("tableSpace") String tableSpace);

    /**
     * 判断指定表字段是否有值
     *
     * @param tableName 表名
     * @param field     表字段
     * @return
     */
    int isNullField(@Param("tableName") String tableName, @Param("field") String field);

    /**
     * 修改字段（目前支持表字段类型和varchar的长度）
     *
     * @param tableName 表名
     * @param field     字段
     */
    void updateField(@Param("tableName") String tableName, @Param("field") GenerateTableFieldDo field);

    /**
     * 获取所有表字段类型
     *
     * @param tableName 表名
     * @return
     */
    List<FieldTypeDo> getTypes(@Param("tableName") String tableName);
}
