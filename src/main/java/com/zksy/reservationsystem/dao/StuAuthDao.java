package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.po.StuAuthPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生认证数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface StuAuthDao {

    /**
     * 通过工号获取学生认证信息
     */
    StuAuthPo queryStuAuthPoByStudentId(String studentId);

    /**
     * 通过工号获取学生认证信息
     */
    StuAuthPo queryStuAuthPoByWxOpenId(String wxOpenId);

    /**
     * 新增学生认证信息
     */
    Boolean insertStuAuth(String uname, String passwd);

    /**
     * 删除学生认证信息
     */
    Boolean deleteStuAuth(String studentId);

    /**
     * 修改学生认证状态
     */
    Boolean updateStuAuth(StuAuthPo stuAuthPo);

    /**
     * 获取学生认证信息包括已删除
     */
    StuAuthPo queryStuAuthPoWithDeleted(String studentId);

    /**
     * 绑定微信
     */
    Boolean boundWithWechat(String uname, String wxOpenId);

    /**
     * 解除微信绑定
     */
    Boolean unboundWithWechat(String uname);
}
