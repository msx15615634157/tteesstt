package com.panshicloud.base.parameter;

/**
 * <p>
 * option对象
 * </>
 *
 * @author xuwenqiang
 * @sinse 2023/5/4
 */

public class Option {

    private String code;

    private String value;

    @Override
    public String toString() {
        return "Option{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
