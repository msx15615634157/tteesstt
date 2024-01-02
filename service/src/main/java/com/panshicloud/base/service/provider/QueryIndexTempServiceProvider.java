package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.QueryIndexTemp;
import com.panshicloud.base.dao.mapper.QueryIndexTempMapper;
import com.panshicloud.base.remote.service.IQueryIndexTempService;
import com.panshicloud.base.service.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yantao
 * @since 2023-12-6
 */

@DubboService
@Slf4j
public class QueryIndexTempServiceProvider extends ServiceImpl<QueryIndexTempMapper, QueryIndexTemp> implements IQueryIndexTempService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private QueryIndexTempMapper queryIndexTempMapper;

    @Override
    public void insertBatch(List<String> queryIdList, String md5) {
        // 批量操作
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        QueryIndexTempMapper organizationIndexTempMapper = sqlSession.getMapper(QueryIndexTempMapper.class);
        // 循环
        int count = 0;
        int batch = 10000;
        for (String queryId : queryIdList) {
            organizationIndexTempMapper.insert(queryId, md5);
            count++;
            if (count % batch == 0) {
                // 提交
                sqlSession.commit();
                sqlSession.clearCache();
                count = 0;
            }
        }
        if (count > 0) {
            // 提交
            sqlSession.commit();
            sqlSession.clearCache();
        }
    }

    @Override
    public String update(List<String> queryIdList) {
        long s = System.currentTimeMillis();
        String md5 = Md5Util.transToMD5(queryIdList.toString());
        Integer countByMd5 = getCountByMd5(md5);
        log.info("获取统计数花费时间，花费时间：{}ms", System.currentTimeMillis() - s);
        if (countByMd5 == 0) {
            insertBatch(queryIdList, md5);
            log.info("临时表塞数据花费时间，花费时间：{}ms", System.currentTimeMillis() - s);
        }
        return md5;
    }

    /**
     * 定时清空临时表
     */
    @Override
    public void truncateTmp() {
        queryIndexTempMapper.truncateTmp();
    }

    @Override
    public Integer getCountByMd5(String md5) {
        return lambdaQuery().eq(QueryIndexTemp::getMd5, md5).count();
    }
}
