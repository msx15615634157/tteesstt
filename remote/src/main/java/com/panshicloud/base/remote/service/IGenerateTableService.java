package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.FieldDto;
import com.panshicloud.base.remote.dto.GenerateTableFieldDto;
import com.panshicloud.base.remote.dto.TableIndexDto;
import com.panshicloud.base.remote.dto.TableStructureDto;

import java.util.List;
import java.util.Map;

/**
 * @Author wanglibin
 * @Date Created in 2022/3/17 16:41
 * @Description GenerateDataTableService.java
 * @Version 1.0
 */
public interface IGenerateTableService {

    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return 是否存在
     */
    boolean isExist(String tableName);

    /**
     * 判断表字段是否存在
     *
     * @param tableName 表名
     * @param field     表字段
     * @return
     */
    boolean isFieldExist(String tableName, String field);

    /**
     * 创建表
     *
     * @param tableName 表名
     * @param entity    表字段
     */
    void generate(String tableName, List<GenerateTableFieldDto> entity);

    /**
     * 根据建表语句创建表
     *
     * @param sql sql
     */
    void createTable(String sql);

    /**
     * 重命名
     *
     * @param tableName   表名
     * @param toTableName 修改后的表名
     */
    void rename(String tableName, String toTableName);

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    void delete(String tableName);

    /**
     * 创建索引
     *
     * @param tableName  表名
     * @param indexName  索引名
     * @param columnList 索引字段
     * @param indexType  索引类型  不填时-普通索引  1-唯一索引
     */
    void createIndex(String tableName, String indexName, String columnList, String indexType);

    /**
     * 按表查索引
     *
     * @param tableName 表名
     * @return 索引
     */
    List<String> findIndex(String tableName);

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     */
    void deleteIndex(String indexName);

    /**
     * 新增字段
     *
     * @param tableName 表名称
     * @param field     字段
     */
    void insertField(String tableName, GenerateTableFieldDto field);

    /**
     * 批量新增字段
     *
     * @param tableName
     * @param fieldList
     */
    void batchInsertField(String tableName, List<GenerateTableFieldDto> fieldList);

    /**
     * 删除字段
     *
     * @param tableName 表名称
     * @param fieldName 字段名称
     */
    void deleteField(String tableName, String fieldName);

    /**
     * 批量删除字段
     *
     * @param tableName     表名称
     * @param fieldNameList 字段名称列表
     */
    void batchDeleteField(String tableName, List<String> fieldNameList);

    /**
     * 重命名字段
     *
     * @param tableName   表名称
     * @param fieldName   字段名称
     * @param toFieldName to字段名称
     */
    void renameField(String tableName, String fieldName, String toFieldName);

    /**
     * 查指定表空间所有表
     *
     * @return
     */
    List<Map<String, Object>> findAllTable();

    /**
     * 查指定表的表字段以及表字段注释
     *
     * @param tableName 表名
     * @return
     */
    List<FieldDto> findFieldAndComments(String tableName);

    /**
     * 查指定表的建表语句
     *
     * @param tableName 表名
     * @return
     */
    TableStructureDto findTableStructure(String tableName);

    /**
     * 判断指定表字段是否有值
     *
     * @param tableName 表名
     * @param field     表字段
     * @return
     */
    boolean isNullField(String tableName, String field);

    /**
     * 更新字段（目前支持字段类型，字段长度）
     *
     * @param tableName 表名
     * @param field 字段
     */
    void updateField(String tableName, GenerateTableFieldDto field);

    /**
     * 获取字段类型
     *
     * @param tableName 表名称
     * @param fieldName 字段名称
     * @return Integer
     */
    Integer getFieldType(String tableName,String fieldName);


    List<TableIndexDto> findTableIndex();
}
