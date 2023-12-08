package com.panshicloud.base.parameter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ExceptionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 抽象参数组
 * </>
 *
 * @author xuwenqiang
 * @date 2023/5/8
 */
public abstract class AbstractGroup {

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

    public AbstractParameter getParameter(Class<?> clazz) {
        for (AbstractTab tab : this.tabs()) {
            for (AbstractParameter parameter : tab.parameters()) {
                if (parameter.getClass() == clazz) {
                    return parameter;
                }
            }
        }
        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未查询到参数");
    }

    /**
     * 获取数据模型
     *
     * @param code 参数编码
     * @param <T>  T
     * @return T
     */
    public abstract <T> T getModel(String code);

    /**
     * 获取参数
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getValue(Class<? extends AbstractParameter> clazz) {
        AbstractParameter find = null;
        for (AbstractTab tab : this.tabs()) {
            for (AbstractParameter parameter : tab.parameters()) {
                if (parameter.getClass() == clazz) {
                    find = parameter;
                    break;
                }
            }
        }
        if (find == null) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "参数未注册");
        }
        return getModel(find.code());
    }

    /**
     * 参数集
     *
     * @return List<AbstractParameter>
     */
    public abstract List<AbstractTab> tabs();


    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", code());
        map.put("name", name());
        map.put("tabs", tabs().toString());
        return JSON.toJSONString(map);
    }

    /**
     * 添加校验
     *
     * @param str str
     * @return AbstractGroup
     */
    public static AbstractGroup build(String str) {
        HashMap<String, Object> map = JSON.parseObject(str, HashMap.class);
        return new AbstractGroup() {
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
            public <T> T getModel(String code) {
                return null;
            }

            @Override
            public List<AbstractTab> tabs() {
                String abstractTabs = (String) map.get("tabs");
                List<JSONObject> tabs = JSON.parseObject(abstractTabs, List.class);
                ArrayList<AbstractTab> rst = new ArrayList<>();
                for (int i = 0; i < tabs.size(); i++) {
                    JSONObject jsonObject = tabs.get(i);
                    String parameters = jsonObject.get("parameters").toString();
                    AbstractTab abstractTab = new AbstractTab() {
                        @Override
                        public String name() {
                            return jsonObject.get("name").toString();
                        }

                        @Override
                        public List<AbstractParameter> parameters() {
                            ArrayList<AbstractParameter> parameterArrayList = new ArrayList<>();
                            List<JSONObject> jsonObjects = JSON.parseObject(parameters, List.class);
                            for (JSONObject object : jsonObjects) {
                                AbstractParameter parameter = new AbstractParameter() {
                                    @Override
                                    public String code() {
                                        return object.get("code").toString();
                                    }

                                    @Override
                                    public String name() {
                                        return object.get("name").toString();
                                    }

                                    @Override
                                    public Integer type() {
                                        return Integer.parseInt(String.valueOf(object.get("type")));
                                    }

                                    @Override
                                    public String description() {
                                        if (object.get("description") == null){
                                            return null;
                                        }
                                        return object.get("description").toString();
                                    }

                                    @Override
                                    public String value() {
                                        return object.get("value").toString();
                                    }

                                    @Override
                                    public Integer dataType() {
                                        if (object.get("dataType") == null){
                                            return null;
                                        }
                                        return (Integer) object.get("dataType");
                                    }

                                    @Override
                                    public List<Option> options() {
                                        return (List<Option>) object.get("options");
                                    }
                                };
                                parameterArrayList.add(parameter);
                            }
                            return parameterArrayList;
                        }
                    };
                    rst.add(abstractTab);
                }
                return rst;
            }
        };
    }


    public static AbstractGroup build(String code, String name, List<AbstractTab> tabs) {
        return new AbstractGroup() {
            @Override
            public String code() {
                return code;
            }

            @Override
            public String name() {
                return name;
            }

            @Override
            public <T> T getModel(String code) {
                return null;
            }

            @Override
            public List<AbstractTab> tabs() {
                return tabs;
            }
        };
    }

}
