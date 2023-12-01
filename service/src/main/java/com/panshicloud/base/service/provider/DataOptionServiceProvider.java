package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.DataOption;
import com.panshicloud.base.dao.mapper.DataOptionMapper;
import com.panshicloud.base.remote.dto.DataOptionCountDto;
import com.panshicloud.base.remote.dto.DataOptionDto;
import com.panshicloud.base.remote.dto.DataOptionInsertDto;
import com.panshicloud.base.remote.service.IDataOptionService;
import com.panshicloud.base.service.constants.RedisKeyCst;
import com.panshicloud.common.constants.JpCommonCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wanglibin
 * @since 2021-09-09
 */
@DubboService
public class DataOptionServiceProvider extends ServiceImpl<DataOptionMapper, DataOption> implements IDataOptionService {

    @Autowired
    private DataOptionMapper dataOptionMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insert(String domain, String code, String name, String description) {
        DataOption dataOption = new DataOption();
        dataOption.setDomain(domain);
        dataOption.setCode(code);
        dataOption.setName(name);
        dataOption.setDescription(description);
        dataOptionMapper.insert(dataOption);
    }

    @Override
    public void insertBatch(List<DataOptionInsertDto> list) {
        List<DataOption> batch = ConvertHelper.tToV(list, DataOption.class);
        saveBatch(batch);
    }

    @Override
    public void delete(String domain, String code) {
        dataOptionMapper.delete(domain, code);
    }

    @Override
    public List<DataOptionDto> findByDomain(String domain, String name) {
        if (StringUtils.isBlank(name)) {
            return findByDomain(domain);
        }
        LambdaQueryWrapper<DataOption> query = new LambdaQueryWrapper<>();
        query.eq(DataOption::getDomain, domain);
        query.like(DataOption::getName, name);
        query.orderByAsc(DataOption::getCode);
        return ConvertHelper.tToV(find(query), DataOptionDto.class);
    }

    @Override
    public List<DataOptionDto> findByDomain(String domain) {
        // 存在缓存则直接返回
        BoundValueOperations operations = redisTemplate.boundValueOps(RedisKeyCst.DATA_OPTION + domain);
        if (operations.get() != null) {
            operations.expire(1, TimeUnit.HOURS);
            return (List<DataOptionDto>) operations.get();
        }
        LambdaQueryWrapper<DataOption> query = new LambdaQueryWrapper<>();
        query.eq(DataOption::getDomain, domain);
        query.orderByAsc(DataOption::getCode);
        List<DataOptionDto> result = ConvertHelper.tToV(find(query), DataOptionDto.class);
        operations.set(result);
        operations.expire(1, TimeUnit.HOURS);
        return result;
    }

    @Override
    public List<DataOptionDto> findByDomainAndCodes(String domain,List<String> codes) {
        LambdaQueryWrapper<DataOption> query = new LambdaQueryWrapper<>();
        query.eq(DataOption::getDomain, domain);
        List<DataOption> list = lambdaQuery()
                .eq(DataOption::getDomain, domain)
                .in(DataOption::getCode, codes)
                .list();
        return ConvertHelper.tToV(list, DataOptionDto.class);
    }

    /**
     * 查询并构建弃用数据
     *
     * @param query
     * @return
     */
    List<DataOption> find(Wrapper<DataOption> query) {
        List<DataOption> list = dataOptionMapper.selectList(query);
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        list.forEach(it -> {
            if (it.getStatus().equals(JpCommonCst.NO)) {
                it.setName(it.getName() + "（已弃用）");
            }
        });
        return list;
    }

    @Override
    public Map<String, String> findByDomainToMap(String domain) {
        return findByDomain(domain, null).stream()
                .collect(Collectors.toMap(DataOptionDto::getCode, DataOptionDto::getName));
    }

    @Override
    public void update(String domain, String code, String name, String description) {
        dataOptionMapper.update(domain, code, name, description);
    }

    @Override
    public void updateStatus(String domain, String code, Integer status) {
        dataOptionMapper.updateStatus(domain, code, status);
    }

    @Override
    public List<DataOptionCountDto> countByDomain() {
        return ConvertHelper.tToV(dataOptionMapper.countByDomain(), DataOptionCountDto.class);
    }

    @Override
    public List<DataOptionDto> findByDomainToTree(String domain, String name) {
        return getTree(this.findByDomain(domain, name), null);
    }

    private List<DataOptionDto> getTree(List<DataOptionDto> list, String code) {
        List<DataOptionDto> result = new ArrayList<>();
        Iterator<DataOptionDto> item = list.iterator();
        while (item.hasNext()) {
            DataOptionDto next = item.next();
            if (StringUtils.equals(next.getParentCode(), code) || next.getParentCode() == code) {
                item.remove();
                result.add(next);
            }
        }
        result.forEach(it -> it.setChildren(getTree(list, it.getCode())));
        return result;
    }

}
