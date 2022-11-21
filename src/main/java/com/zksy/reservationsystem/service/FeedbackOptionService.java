package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.po.FeedbackOptionPo;

import java.util.List;

/**
 * 学生反馈选项服务层
 *
 * @author kkkoke
 * @since 2022/11/21
 */
public interface FeedbackOptionService {

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
}
