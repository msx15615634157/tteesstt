package com.panshicloud.base.service.provider;

import com.panshicloud.base.dao.dos.FieldTypeDo;
import com.panshicloud.base.dao.dos.GenerateTableFieldDo;
import com.panshicloud.base.dao.dos.TableIndexDo;
import com.panshicloud.base.dao.mapper.GenerateTableMapper;
import com.panshicloud.base.remote.constants.CommonCst;
import com.panshicloud.base.remote.dto.*;
import com.panshicloud.base.remote.service.IGenerateTableService;
import com.panshicloud.common.constants.JpCommonCst;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @Author wanglibin
 * @Date Created in 2022/3/17 16:41
 * @Description GenerateDataTableServiceImpl.java
 * @Version 1.0
 */
@DubboService
public class GenerateTableServiceProvider implements IGenerateTableService {

    @Autowired
    private GenerateTableMapper generateDataTableMapper;

    @Value("${spring.datasource.username}")
    private volatile String tableSpace;

    @Override
    public boolean isExist(String tableName) {
        return generateDataTableMapper.isExist(tableName) > 0;
    }

    @Override
    public boolean isFieldExist(String tableName, String field) {
        return generateDataTableMapper.isFieldExist(tableName, field, tableSpace) > 0;
    }

    @Override
    public void generate(String tableName, List<GenerateTableFieldDto> entity) {
        int primaryKeyCount = 0;
        for (GenerateTableFieldDto it : entity) {
            if (StringUtils.isBlank(it.getName())) {
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "字段名称不能为空");
            }
            if (it.getType() == null) {
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "字段类型不能为空");
            }
            if (it.getLength() == null) {
                it.setLength(50);
            }
            if (it.getIsAllowNull() == null) {
                it.setIsAllowNull(JpCommonCst.YES);
            }
            if (it.getIsAutoIncrement() == null) {
                it.setIsAutoIncrement(JpCommonCst.NO);
            }
            if (it.getIsPrimaryKey() == null) {
                it.setIsPrimaryKey(JpCommonCst.NO);
            }
            if (it.getComment() == null) {
                it.setComment("");
            }
            if (it.getIsPrimaryKey() == 1) {
                primaryKeyCount++;
                if (it.getIsAllowNull().equals(JpCommonCst.YES)) {
                    it.setIsAllowNull(JpCommonCst.NO);
                }
            }
        }
        if (primaryKeyCount != 1) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未设置主键或设置多个主键");
        }
        List<GenerateTableFieldDo> generateTableFieldDoList = ConvertHelper.tToV(entity, GenerateTableFieldDo.class);
        generateDataTableMapper.generate(tableName, generateTableFieldDoList);
        generateTableFieldDoList.forEach(it -> {
            generateDataTableMapper.generateComment(tableName, it);
        });
    }

    @Override
    public void createTable(String sql) {
        generateDataTableMapper.createTable(sql);
    }

    @Override
    public void rename(String tableName, String toTableName) {
        generateDataTableMapper.rename(tableName, toTableName);
    }

    @Override
    public void delete(String tableName) {
        generateDataTableMapper.delete(tableName);
    }

    @Override
    public void createIndex(String tableName, String indexName, List<String> columnList, String indexType) {
        generateDataTableMapper.createIndex(tableName, indexName, columnList, indexType);
    }

    @Override
    public List<String> findIndex(String tableName) {
        return generateDataTableMapper.findIndex(tableName, tableSpace);
    }

    @Override
    public List<IndexAndIndexFieldDto> findIndexAndIndexField(String tableName) {
        return ConvertHelper.tToV(generateDataTableMapper.findIndexAndIndexField(tableName, tableSpace), IndexAndIndexFieldDto.class);
    }

    @Override
    public List<IndexAndIndexFieldDto> findAllIndexAndIndexField() {
        return ConvertHelper.tToV(generateDataTableMapper.findAllIndexAndIndexField(tableSpace), IndexAndIndexFieldDto.class);
    }

    @Override
    public void deleteIndex(String indexName) {
        generateDataTableMapper.deleteIndex(indexName);
    }

    @Override
    public void insertField(String tableName, GenerateTableFieldDto field) {
        GenerateTableFieldDo generateTableFieldDo = ConvertHelper.tToV(field, GenerateTableFieldDo.class);
        generateDataTableMapper.insertField(tableName, generateTableFieldDo);
        generateDataTableMapper.generateComment(tableName, generateTableFieldDo);
    }

    @Override
    public void batchInsertField(String tableName, List<GenerateTableFieldDto> fieldList) {
        List<GenerateTableFieldDo> generateTableFieldList = ConvertHelper.tToV(fieldList, GenerateTableFieldDo.class);
        generateDataTableMapper.batchInsertField(tableName, generateTableFieldList);
        generateTableFieldList.forEach(it -> {
            generateDataTableMapper.generateComment(tableName, it);
        });
    }

    @Override
    public void deleteField(String tableName, String fieldName) {
        generateDataTableMapper.deleteField(tableName, fieldName);
    }

    @Override
    public void batchDeleteField(String tableName, List<String> fieldNameList) {
        generateDataTableMapper.batchDeleteField(tableName, fieldNameList);
    }

    @Override
    public void renameField(String tableName, String fieldName, String toFieldName) {
        generateDataTableMapper.renameField(tableName, fieldName, toFieldName);
    }

    @Override
    public List<Map<String, Object>> findAllTable() {
        return generateDataTableMapper.findAllTable(tableSpace);
    }

    @Override
    public List<FieldDto> findFieldAndComments(String tableName, String dataSource) {
        return ConvertHelper.tToV(generateDataTableMapper.findFieldAndComments(tableName, dataSource), FieldDto.class);
    }

    @Override
    public TableStructureDto findTableStructure(String tableName) {
        TableStructureDto tableStructureDto = new TableStructureDto();
        String tableStructure = generateDataTableMapper.findTableStructure(tableName, tableSpace);
        tableStructureDto.setTableStructure(tableStructure);
        tableStructureDto.setTableSpace(tableSpace);
        return tableStructureDto;
    }

    @Override
    public boolean isNullField(String tableName, String field) {
        return generateDataTableMapper.isNullField(tableName, field) == 0;
    }

    @Override
    public void updateField(String tableName, GenerateTableFieldDto field) {
        String fieldName = field.getName();
        Integer oldType = this.getFieldType(tableName, fieldName);
        if (CommonCst.TABLE_FIELD_TYPE_CLOB == field.getType() || oldType == CommonCst.TABLE_FIELD_TYPE_CLOB) {
            GenerateTableFieldDo generateTableField = new GenerateTableFieldDo();
            generateTableField.setLength(field.getLength());
            generateTableField.setType(field.getType());
            generateTableField.setName(fieldName);
            generateDataTableMapper.deleteField(tableName, fieldName);
            generateDataTableMapper.insertField(tableName, generateTableField);
        } else {
            generateDataTableMapper.updateField(tableName, ConvertHelper.tToV(field, GenerateTableFieldDo.class));
        }
    }

    @Override
    public Integer getFieldType(String tableName, String fieldName) {
        List<FieldTypeDo> types = generateDataTableMapper.getTypes(tableName, tableSpace);
        Optional<FieldTypeDo> optional = types.stream().filter(it -> it.getFieldName().equalsIgnoreCase(fieldName)).findAny();
        if (!optional.isPresent()) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "字段不存在");
        }
        String type = optional.get().getType();
        switch (type) {
            case CommonCst.TABLE_FIELD_VARCHAR2:
            case CommonCst.TABLE_FIELD_VARCHAR:
                return CommonCst.TABLE_FIELD_TYPE_VARCHAR;
            case CommonCst.TABLE_FIELD_DATE:
                return CommonCst.TABLE_FIELD_TYPE_DATE;
            case CommonCst.TABLE_FIELD_CLOB:
                return CommonCst.TABLE_FIELD_TYPE_CLOB;
            default:
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "不支持的字段类型");
        }
    }

    @Override
    public List<TableIndexDto> findTableIndex() {
        List<TableIndexDo> tableIndex = generateDataTableMapper.findTableIndex(tableSpace);
        return ConvertHelper.tToV(tableIndex, TableIndexDto.class);
    }

}
