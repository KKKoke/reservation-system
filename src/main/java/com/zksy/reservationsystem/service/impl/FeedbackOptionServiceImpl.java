package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.dao.FeedbackOptionDao;
import com.zksy.reservationsystem.domain.po.FeedbackOptionPo;
import com.zksy.reservationsystem.service.FeedbackOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 学生反馈选项服务层实现类
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Service
@RequiredArgsConstructor
public class FeedbackOptionServiceImpl implements FeedbackOptionService {

    private final FeedbackOptionDao feedbackOptionDao;

    @Override
    public Boolean insertFeedbackOption(String option) {
        return feedbackOptionDao.insertFeedbackOption(option);
    }

    @Override
    public Boolean deleteFeedbackOption(Integer optionId) {
        return !ObjectUtils.isEmpty(feedbackOptionDao.queryFeedbackOptionByOptionId(optionId))
                && feedbackOptionDao.deleteFeedbackOption(optionId);
    }

    @Override
    public List<FeedbackOptionPo> queryFeedbackOptionList() {
        return feedbackOptionDao.queryFeedbackOptionList();
    }

    @Override
    public FeedbackOptionPo queryFeedbackOptionByOptionId(Integer optionId) {
        return feedbackOptionDao.queryFeedbackOptionByOptionId(optionId);
    }
}
