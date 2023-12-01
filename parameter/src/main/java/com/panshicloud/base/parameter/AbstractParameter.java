package com.panshicloud.base.parameter;

import com.alibaba.fastjson.JSON;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 抽象参数
 * </>
 *
 * @author xuwenqiang
 * @sinse 2023/4/27
 */
public abstract class AbstractParameter {

    /**
     * 枚举
     */
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_SELECT = 1;
    public static final int TYPE_SWITCH = 2;
    public static final int TYPE_SINGLE_TEXT = 3;

    public static final int VALUE_TYPE_STRING = 0;
    public static final int VALUE_TYPE_INT = 1;
    public static final int VALUE_TYPE_FLOAT = 2;
    public static final int VALUE_TYPE_DOUBLE = 3;

    private String value;

    /**
     * code存在库里
     *
     * @return String
     */
    public abstract String code();

    /**
     * 展示给前端
     *
     * @return String
     */
    public abstract String name();

    /**
     * 参数类型
     *
     * @return Integer
     */
    public abstract Integer type();

    /**
     * 参数描述
     *
     * @return String
     */
    public abstract String description();

    /**
     * 参数默认值
     *
     * @return String
     */
    public abstract String value();

    /**
     * 参数类型
     *
     * @return String
     */
    public abstract Integer dataType();


    public String getValue() {
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        return value();
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 展示给前端
     * 后面存下拉框内容
     *
     * @return Option
     */
    public abstract List<Option> options();

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", code());
        map.put("name", name());
        map.put("value", value());
        map.put("type", type());
        map.put("description", description());
        map.put("options", options());
        map.put("dataType", dataType());
        return JSON.toJSONString(map);
    }

    /**
     * 添加校验
     *
     * @param str str
     * @return AbstractParameter
     */
    public static AbstractParameter build(String str) {
        HashMap<String, Object> map = JSON.parseObject(str, HashMap.class);
        return new AbstractParameter() {
            @Override
            public String code() {
                Object code = map.get("code");
                if (!(code instanceof String)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "编码参数必须为字符串");
                }
                return (String) code;
            }

            @Override
            public String name() {
                Object name = map.get("name");
                if (!(name instanceof String)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "名称必须为字符串");
                }
                return (String) name;
            }

            @Override
            public Integer type() {
                Object type = map.get("type");
                if (!(type instanceof Integer)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "类型必须为数字");
                }
                return (Integer) type;
            }

            @Override
            public String description() {
                Object description = map.get("description");
                if (!(description instanceof String)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "描述必须为字符串");
                }
                return (String) description;
            }

            @Override
            public String value() {
                return null;
            }

            @Override
            public Integer dataType() {
                Object dataType = map.get("dataType");
                if (!(dataType instanceof Integer)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "描述必须为整型");
                }
                return (Integer) dataType;
            }


            @Override
            public List<Option> options() {
                if (type() != TYPE_SELECT) {
                    return null;
                }
                Object options = map.get("options");
                if (!(options instanceof List)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "options必须为List集合");
                }
                for (Object option : (List) options) {
                    if (!(option instanceof Option)) {
                        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "options中必须为option对象");
                    }
                }
                return (List<Option>) options;
            }
        };
    }


}