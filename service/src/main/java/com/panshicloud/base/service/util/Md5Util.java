package com.panshicloud.base.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @Author yantao
 * @since 2023/12/5
 **/
public class Md5Util {

    /**
     * 使用MD5对字符串进行加密
     */
    public static String transToMD5(String string) {

        try {
            //获取消息摘要对象
            MessageDigest md = MessageDigest.getInstance("md5");
            //通过MD5计算摘要，返回byte数组形式
            byte[] b = md.digest(string.getBytes());
            /*
             * Base64算法将 byte数组转换成可保存的字符串（a-z A-Z 0-9 * /）形式
             */
            String str = Base64.getEncoder().encodeToString(b);
            return str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
