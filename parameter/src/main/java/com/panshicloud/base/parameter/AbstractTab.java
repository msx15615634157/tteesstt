package com.panshicloud.base.parameter;

import com.alibaba.fastjson.JSON;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ExceptionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 抽象页签
 * </>
 *
 * @author xuwenqiang
 * @sinse 2023/5/8
 */

public abstract class AbstractTab {


    /**
     * 展示给前端
     *
     * @return String
     */
    public abstract String name();

    /**
     * 参数集
     *
     * @return List<AbstractParameter>
     */
    public abstract List<AbstractParameter> parameters();

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("name", name());
        map.put("parameters", parameters().toString());
        return JSON.toJSONString(map);
    }

    /**
     * 添加校验
     *
     * @param str str
     * @return AbstractGroup
     */
    public static AbstractTab build(String str) {
        Map<String, Object> map = JSON.parseObject(str, HashMap.class);
        return new AbstractTab() {
            @Override
            public String name() {
                Object name = map.get("name");
                if (!(name instanceof String)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "名称必须为字符串");
                }
                return (String) name;
            }

            @Override
            public List<AbstractParameter> parameters() {
                String AbstractParameters = (String) map.get("parameters");
                List parameters = JSON.parseObject(AbstractParameters, List.class);
                if (!(parameters instanceof List)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "options必须为List集合");
                }
                return (List<AbstractParameter>) parameters;
            }
        };
    }


    public static AbstractTab build(String name, List<AbstractParameter> parameters) {
        return new AbstractTab() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public List<AbstractParameter> parameters() {
                return parameters;
            }
        };
    }
}
