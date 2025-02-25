package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.QueryIndexTemp;
import com.panshicloud.base.dao.mapper.QueryIndexTempMapper;
import com.panshicloud.base.remote.service.IQueryIndexTempService;
import com.panshicloud.base.service.constants.RedisKeyCst;
import com.panshicloud.base.service.util.Md5Util;
import com.panshicloud.common.lock.SupplierLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
    @Autowired
    private SupplierLock supplierLock;

    @Override
    public void insertBatch(List<String> queryIdList, String md5) {
        // 批量操作
        List<QueryIndexTemp> batch = new ArrayList<>();
        int i = 1;
        for (String queryId : queryIdList) {
            QueryIndexTemp queryIndexTemp = new QueryIndexTemp();
            queryIndexTemp.setQueryId(queryId);
            queryIndexTemp.setMd5(md5);
            queryIndexTemp.setSort(i);
            batch.add(queryIndexTemp);
            i++;
        }
        saveBatch(batch);
//        // 批量操作
//        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//        QueryIndexTempMapper organizationIndexTempMapper = sqlSession.getMapper(QueryIndexTempMapper.class);
//        try {
//            // 循环
//            int count = 0;
//            int batch = 10000;
//            for (String queryId : queryIdList) {
//                organizationIndexTempMapper.insert(queryId, md5);
//                count++;
//                if (count % batch == 0) {
//                    // 提交
//                    sqlSession.flushStatements();
//                    sqlSession.commit();
//                    sqlSession.clearCache();
//                    count = 0;
//                }
//            }
//            if (count > 0) {
//                // 提交
//                sqlSession.flushStatements();
//                sqlSession.commit();
//                sqlSession.clearCache();
//            }
//        } catch (Exception e) {
//            sqlSession.rollback();
//        } finally {
//            sqlSession.close();
//        }

    }

    @Override
    public String update(List<String> queryIdList) {
        long s = System.currentTimeMillis();
        String md5 = Md5Util.transToMD5(queryIdList.toString());
        Integer countByMd5 = getCountByMd5(md5);
        log.info("获取统计数花费时间，花费时间：{}ms", System.currentTimeMillis() - s);
        if (countByMd5 == 0) {
            // 更新表数据，先删除，后新增
            supplierLock.exec(md5,
                    () -> true,
                    () -> {
                        insertBatch(queryIdList, md5);
                    }
            );
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
