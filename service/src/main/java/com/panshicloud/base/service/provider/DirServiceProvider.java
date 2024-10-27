package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/27 11:33
 */
@DubboService
public class DirServiceProvider extends ServiceImpl<DirMapper, Dir> implements IDirService {

    @Override
    @CacheEvict(value = RedisKeyCst.DIR_TREE, key = "#dir.type")
    public void insert(DirInsertDto dir) {
        if (get(dir.getType(), dir.getCode()) != null) {
            throw ExceptionHelper.newException(ErrorCodeCst.DIR_IS_EXIST, "目录编码已存在，不能新增！");
        }
        if (dir.getSort() == null) {
            dir.setSort(0);
        }
        save(ConvertHelper.tToV(dir, Dir.class));
    }

    @Override
    @CacheEvict(value = RedisKeyCst.DIR_TREE, key = "#dir.type")
    public void update(DirUpdateDto dir) {
        if (get(dir.getType(), dir.getCode()) == null) {
            throw ExceptionHelper.newException(ErrorCodeCst.DIR_IS_NOT_EXIST, "目录编码不存在，不能修改！");
        }
        LambdaUpdateChainWrapper<Dir> wrapper = lambdaUpdate().eq(Dir::getType, dir.getType()).eq(Dir::getCode, dir.getCode());
        if (StringUtils.isNotBlank(dir.getName())) {
            wrapper.set(Dir::getName, dir.getName());
        }
        if (StringUtils.isNotBlank(dir.getParentCode())) {
            wrapper.set(Dir::getParentCode, dir.getParentCode());
        }
        if (dir.getSort() != null) {
            wrapper.set(Dir::getSort, dir.getSort());
        }
        wrapper.update();
    }

    @Override
    @CacheEvict(value = RedisKeyCst.DIR_TREE, key = "#type")
    public void delete(String type, String code) {
        DirDto dirDto = get(type, code);
        if (dirDto == null) {
            throw ExceptionHelper.newException(ErrorCodeCst.DIR_IS_EXIST, "目录编码已存在，不能删除！");
        }
        Set<String> delCodes = new HashSet<>();
        Set<String> parentCodes = new HashSet<>();
        parentCodes.add(dirDto.getCode());
        delCodes.add(dirDto.getCode());
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
        lambdaUpdate().eq(Dir::getType, type).in(Dir::getCode, delCodes).remove();
    }

    @Override
    public DirDto get(String type, String code) {
        return ConvertHelper.tToV(lambdaQuery().eq(Dir::getType, type).eq(Dir::getCode, code).one(), DirDto.class);
    }

    @Override
    public List<DirDto> find(String type) {
        List<Dir> list = lambdaQuery().eq(Dir::getType, type).orderByAsc(Dir::getSort).list();
        if (list == null) {
            return new ArrayList<>(0);
        }
        return ConvertHelper.tToV(list, DirDto.class);
    }

    @Override
    @Cacheable(value = RedisKeyCst.DIR_TREE, key = "#type")
    public List<DirTreeDto> findToTree(String type) {
        List<DirDto> dirList = find(type);
        if (CollectionUtils.isEmpty(dirList)) {
            return new ArrayList<>(0);
        }
        Map<String, List<DirDto>> dirMap = new HashMap<>();
        for (DirDto dirDto : dirList) {
            List<DirDto> dirDtos = dirMap.computeIfAbsent(dirDto.getParentCode(), key -> new ArrayList<>());
            dirDtos.add(dirDto);
        }
        return toTree(null, dirMap);
    }

    private List<DirTreeDto> toTree(String parentCode, Map<String, List<DirDto>> dirMap) {
        List<DirDto> dirDtos = dirMap.get(parentCode);
        if (CollectionUtils.isEmpty(dirDtos)) {
            return null;
        }
        List<DirTreeDto> dirTrees = ConvertHelper.tToV(dirDtos, DirTreeDto.class);
        for (DirTreeDto dir : dirTrees) {
            dir.setChildren(toTree(dir.getCode(), dirMap));
        }
        return dirTrees;
    }

}
