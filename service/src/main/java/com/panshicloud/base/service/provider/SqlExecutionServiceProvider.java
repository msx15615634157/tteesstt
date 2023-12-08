package com.panshicloud.base.service.provider;

import com.alibaba.nacos.common.utils.StringUtils;
import com.panshicloud.base.remote.dto.QueryDto;
import com.panshicloud.base.remote.dto.UpdateDto;
import com.panshicloud.base.remote.service.ISqlExecutionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

/**
 * @author xingxingfa
 * @BelongProject: base
 * @BelongPackage: com.panshicloud.base.service.provider
 * @CreateTime: 2022/11/17
 */
@DubboService
public class SqlExecutionServiceProvider implements ISqlExecutionService {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public QueryDto getQuerySql(String sql) {
        String base64Decrypt = this.base64Decrypt(sql);
        if (StringUtils.isBlank(base64Decrypt)) {
            return new QueryDto();
        }
        QueryDto queryDto = new QueryDto();
        SqlExecutionServiceProvider jdbc = new SqlExecutionServiceProvider();
        Connection conn = jdbc.getConnection(driver, url, user, password);
        List<String> head = new ArrayList<>();
        List<Map<String, String>> data = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(base64Decrypt);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                head.add(metaData.getColumnName(i + 1));
            }
            while (resultSet.next()) {
                Map<String, String> rowData = new HashMap<>(16);
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    rowData.put(head.get(i), resultSet.getString(head.get(i)));
                }
                data.add(rowData);
            }
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            queryDto.setError(e.getMessage());
            e.printStackTrace();
        }
        queryDto.setHead(head);
        queryDto.setData(data);
        return queryDto;
    }

    @Override
    public UpdateDto getUpdateSql(String sql) {
        String base64Decrypt = this.base64Decrypt(sql);
        if (StringUtils.isBlank(base64Decrypt)) {
            return new UpdateDto();
        }
        int i = 0;
        UpdateDto updateDto = new UpdateDto();
        SqlExecutionServiceProvider jdbc = new SqlExecutionServiceProvider();
        Connection conn = jdbc.getConnection(driver, url, user, password);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(base64Decrypt);
            i = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            updateDto.setError(e.getMessage());
            e.printStackTrace();
        }
        updateDto.setCorrect("受影响的行数：" + i);
        return updateDto;
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    public Connection getConnection(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 基于base64解密
     */
    public String base64Decrypt(String value) {
        byte[] b;
        if (value != null) {
            b = Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8));
            value = new String(b, StandardCharsets.UTF_8);
        }
        return value;
    }
}