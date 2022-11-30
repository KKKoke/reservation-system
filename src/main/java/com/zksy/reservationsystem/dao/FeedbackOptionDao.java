package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.po.FeedbackOptionPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生反馈内容选项数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface FeedbackOptionDao {

    /**
     * 添加学生反馈选项
     */
    Boolean insertFeedbackOption(String option);

    /**
     * 删除学生反馈选项
     */
    Boolean deleteFeedbackOption(Integer optionId);

    /**
     * 获取学生反馈选项列表
     */
    List<FeedbackOptionPo> queryFeedbackOptionList();

    /**
     * 通过反馈选项id获取反馈选项
     */
    FeedbackOptionPo queryFeedbackOptionByOptionId(Integer optionId);

    /**
     * 通过反馈选项id获取反馈选项包括已删除
     */
    FeedbackOptionPo queryFeedbackOptionWithDeletedByOptionId(Integer optionId);
}
