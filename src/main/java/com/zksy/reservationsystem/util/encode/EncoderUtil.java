package com.zksy.reservationsystem.util.encode;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 配置加密工具
 *
 * @author kkkoke
 * @since 2022/11/20
 */
public class EncoderUtil {
    private static final byte[] SECRET_KEY=new byte[]{43, -124, 54, 16, 10, -62, 34, -66, -93, -118, 37, 11, -81, -2, -107, 23};
    private static final cn.hutool.crypto.symmetric.AES AES = SecureUtil.aes(SECRET_KEY);
    private static final String ENCODER_PREFIX="encode:";
    /**
     * 解密
     */
    public static String decode(String encodePwd) {
        return AES.decryptStr(encodePwd, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 加密
     */
    public static String encode(String rawPwd){
        return AES.encryptHex(rawPwd);
    }

    /**
     * 如果当前字符串以指定前缀开始,则对字符串进行解密
     * 否则对字符串进行加密
     */
    public static String decodeWithPrefixOrEncode(String str){
        if(str.startsWith(ENCODER_PREFIX)){
            return decode(str.substring(ENCODER_PREFIX.length()));
        }
        return ENCODER_PREFIX+encode(str);
    }
}
