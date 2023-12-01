package com.panshicloud.base.remote.dto;

import com.panshicloud.base.parameter.Option;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 分组参数Dto
 * </>
 *
 * @author xuwenqiang
 * @date 2023/6/15
 */
@Data
public class GroupParameterDto {

    private String code;

    private String name;

    private List<Tab> tabs;

    @Data
    public static class Tab {

        public String name;

        public List<Parameter> parameters;

        @Data
        public static class Parameter {

            /**
             * code存在库里
             */
            private String code;
            /**
             * 展示给前端
             */
            private String name;

            /**
             * 参数类型
             */
            private Integer type;

            /**
             * 值
             */
            private Object value;

            /**
             * 展示给前端
             */
            private List<Option> options;

            /**
             * 描述
             */
            private String description;

        }
    }

}
