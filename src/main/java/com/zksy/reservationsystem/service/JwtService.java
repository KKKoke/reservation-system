package com.zksy.reservationsystem.service;

/**
 * jwt服务层
 *
 * @author kkkoke
 * @since 2022/9/17
 */
public interface JwtService {

    /**
     * 登录功能
     *
     * @param type     登录类型  1是学生登录，2是老师登录
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(Integer type, String username, String password);

    /**
     * 刷新Token
     *
     * @param oldToken 旧token
     */
    String refreshToken(String oldToken);

    /**
     * 学生登录功能
     *
     * @param stuUsername 学生用户名
     * @param stuPassword 学生密码
     * @return 生成的JWT的token
     */
    String stuLogin(String stuUsername, String stuPassword);

    /**
     * 老师登录功能
     *
     * @param teaUsername 老师用户名
     * @param teaPassword 老师密码
     * @return 生成的JWT的token
     */
    String teaLogin(String teaUsername, String teaPassword);

    /**
     * 从token中获取登录用户名
     *
     * @return 登录用户名
     */
    String getUserNameFromToken(String token);

    /**
     * 判断token是否已经失效
     */
    boolean isTokenExpired(String token);

    /**
     * 获取登录类型
     */
    String getClaimTypeFromToken(String token);

    /**
     * 绑定微信
     */
    Boolean boundWithWechat(String uname, Integer type, String code);

    /**
     * 解除微信绑定
     */
    Boolean unBoundWithWechat(String uname, Integer type);
}
