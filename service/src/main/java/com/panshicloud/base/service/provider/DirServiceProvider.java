package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.Dir;
import com.panshicloud.base.dao.mapper.DirMapper;
import com.panshicloud.base.remote.dto.DirDto;
import com.panshicloud.base.remote.dto.DirInsertDto;
import com.panshicloud.base.remote.dto.DirTreeDto;
import com.panshicloud.base.remote.dto.DirUpdateDto;
import com.panshicloud.base.remote.service.IDirService;
import com.panshicloud.base.service.constants.ErrorCodeCst;
import com.panshicloud.base.service.constants.RedisKeyCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/27 11:33
 */
@DubboService
public class DirServiceProvider extends ServiceImpl<DirMapper, Dir> implements IDirService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insert(DirInsertDto dir) {
        if (lambdaQuery()
                .eq(Dir::getType, dir.getType())
                .eq(Dir::getCode, dir.getCode())
                .count() > 0) {
            throw ExceptionHelper.newException(ErrorCodeCst.DIR_IS_EXIST, "目录编码已存在，新增失败");
        }
        save(ConvertHelper.tToV(dir, Dir.class));
        this.deleteRedisCache(dir.getType());
    }

    @Override
    public void update(DirUpdateDto dir) {
        if (lambdaQuery()
                .eq(Dir::getType, dir.getType())
                .eq(Dir::getCode, dir.getCode())
                .count() < 1) {
            throw ExceptionHelper.newException(ErrorCodeCst.DIR_IS_NOT_EXIST, "目录编码不存在，不能修改");
        }
        if (StringUtils.isNotBlank(dir.getName())) {
            lambdaUpdate()
                    .eq(Dir::getType, dir.getType())
                    .eq(Dir::getCode, dir.getCode())
                    .set(Dir::getName, dir.getName())
                    .set(Dir::getSort, dir.getSort())
                    .update();
        }
        this.deleteRedisCache(dir.getType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSort(Integer type, String code, String parentCode, Integer sort) {
        DirDto current = this.get(type, code);
        Integer currentSort = current.getSort();
        String currentParentCode = current.getParentCode();
        // 目录内拖拽排序
        if (StringUtils.equals(parentCode, currentParentCode)) {
            // 向上拖拽
            if (sort < currentSort) {
                lambdaUpdate()
                        .eq(Dir::getType, type)
                        .eq(Dir::getParentCode, currentParentCode)
                        .ge(Dir::getSort, sort)
                        .lt(Dir::getSort, currentSort)
                        .setSql("sort = sort + 1")
                        .update();
            }
            // 向下拖拽
            if (sort > currentSort) {
                lambdaUpdate()
                        .eq(Dir::getType, type)
                        .eq(Dir::getParentCode, currentParentCode)
                        .gt(Dir::getSort, currentSort)
                        .le(Dir::getSort, sort)
                        .setSql("sort = sort - 1")
                        .update();
            }
        } else {
            // 目录外拖拽
            lambdaUpdate()
                    .eq(Dir::getType, type)
                    .eq(Dir::getParentCode, currentParentCode)
                    .gt(Dir::getSort, currentSort)
                    .setSql("sort = sort - 1")
                    .update();
            lambdaUpdate()
                    .eq(Dir::getType, type)
                    .eq(Dir::getParentCode, parentCode)
                    .ge(Dir::getSort, sort)
                    .setSql("sort = sort + 1")
                    .update();
        }
        lambdaUpdate()
                .eq(Dir::getType, type)
                .eq(Dir::getCode, code)
                .set(Dir::getParentCode, parentCode)
                .set(Dir::getSort, sort)
                .update();
        this.deleteRedisCache(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer type, String code) {
        DirDto current = get(type, code);
        if (current == null) {
            throw ExceptionHelper.newException(ErrorCodeCst.DIR_IS_EXIST, "目录编码不存在");
        }
        Set<String> delCodes = new HashSet<>();
        Set<String> parentCodes = new HashSet<>();
        parentCodes.add(code);
        delCodes.add(code);
        while (!parentCodes.isEmpty()) {
            List<Dir> list = lambdaQuery().eq(Dir::getType, type).in(Dir::getParentCode, parentCodes).list();
            parentCodes.clear();
            if (!CollectionUtils.isEmpty(list)) {
                for (Dir dir : list) {
                    parentCodes.add(dir.getCode());
                    delCodes.add(dir.getCode());
                }
            }
        }
        // 修改同级目录的排序字段
        lambdaUpdate()
                .eq(Dir::getType, type)
                .eq(Dir::getParentCode, current.getParentCode())
                .gt(Dir::getSort, current.getSort())
                .setSql("sort = sort - 1")
                .update();
        // 删除目录及下级目录
        lambdaUpdate().eq(Dir::getType, type).in(Dir::getCode, delCodes).remove();
        this.deleteRedisCache(type);
    }

    @Override
    public DirDto get(Integer type, String code) {
        return ConvertHelper.tToV(lambdaQuery().eq(Dir::getType, type).eq(Dir::getCode, code).one(), DirDto.class);
    }

    @Override
    @Cacheable(value = RedisKeyCst.DIR_TREE, key = "#type")
    public List<DirDto> find(Integer type) {
        List<Dir> list = lambdaQuery().eq(Dir::getType, type).orderByAsc(Dir::getSort).list();
        if (list == null) {
            return new ArrayList<>();
        }
        return ConvertHelper.tToV(list, DirDto.class);
    }

    @Override
    public List<DirTreeDto> findToTree(Integer type) {
        return toTree("", ConvertHelper.tToV(this.find(type), DirTreeDto.class));
    }

    @Override
    public List<DirDto> findAllChildrenAndSelf(Integer type, String dirCode) {
        List<DirDto> result = this.getChildren(this.find(type), dirCode);
        if (StringUtils.isNotBlank(dirCode)) {
            DirDto dirDto = this.get(type, dirCode);
            if (dirDto == null) {
                throw ExceptionHelper.newException(ErrorCodeCst.DIR_IS_EXIST, dirCode + " 目录编码不存在");
            }
            result.add(dirDto);
        }
        return result;
    }

    private List<DirDto> getChildren(List<DirDto> dirList, String parentCode) {
        List<DirDto> result = new ArrayList<>();
        Iterator<DirDto> item = dirList.iterator();
        while (item.hasNext()) {
            DirDto next = item.next();
            if (StringUtils.isBlank(parentCode)) {
                if (StringUtils.isBlank(next.getParentCode())) {
                    item.remove();
                    result.add(next);
                }
            } else {
                if (StringUtils.equals(next.getParentCode(), parentCode)) {
                    item.remove();
                    result.add(next);
                }
            }
        }
        // 查询下下级目录
        List<DirDto> children = new ArrayList<>(result);
        for (DirDto dir : children) {
            result.addAll(this.getChildren(dirList, dir.getCode()));
        }
        return result;
    }

    private List<DirTreeDto> toTree(String parentCode, List<DirTreeDto> dirList) {
        List<DirTreeDto> result = new ArrayList<>();
        Iterator<DirTreeDto> item = dirList.iterator();
        while (item.hasNext()) {
            DirTreeDto next = item.next();
            if (StringUtils.isBlank(parentCode)) {
                if (StringUtils.isBlank(next.getParentCode())) {
                    item.remove();
                    result.add(next);
                }
            } else {
                if (StringUtils.equals(next.getParentCode(), parentCode)) {
                    item.remove();
                    result.add(next);
                }
            }
        }
        result.forEach(it -> it.setChildren(toTree(it.getCode(), dirList)));
        return result;
    }

    private void deleteRedisCache(Integer type) {
        redisTemplate.delete(RedisKeyCst.DIR_TREE + ":" + type);
    }
}
