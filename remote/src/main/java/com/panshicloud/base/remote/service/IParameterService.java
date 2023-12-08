package com.panshicloud.base.remote.service;

/**
 * <p>
 * 参数接口
 * </>
 *
 * @author xuwenqiang
 * @sinse 2023/5/5
 */
public interface IParameterService {

    /**
     * 通过编码获取参数
     *
     * @param groupCode 分组编码
     * @return String
     */
    String getParameter(String groupCode);

}
