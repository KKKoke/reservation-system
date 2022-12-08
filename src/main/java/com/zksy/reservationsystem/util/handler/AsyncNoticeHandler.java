package com.zksy.reservationsystem.util.handler;

import com.zksy.reservationsystem.dao.StuAuthDao;
import com.zksy.reservationsystem.dao.TeaAuthDao;
import com.zksy.reservationsystem.domain.vo.NoticeDataVo;
import com.zksy.reservationsystem.util.auth.WechatUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 异步任务处理
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncNoticeHandler {

    private final TeaAuthDao teaAuthDao;

    private final StuAuthDao stuAuthDao;

    @SneakyThrows
    @Async
    public void sendNotice(String jobId, String studentId, NoticeDataVo noticeDataVo) {
        log.info("{} - {} - 开始执行异步通知", studentId, jobId);
        String teaOpenId = teaAuthDao.queryWxOpenIdByUname(jobId);
        String stuOpenId = stuAuthDao.queryWxOpenIdByUname(studentId);
        if (!ObjectUtils.isEmpty(teaOpenId)) {
            WechatUtil.sendNotice(teaOpenId, noticeDataVo);
        }
        if (!ObjectUtils.isEmpty(stuOpenId)) {
            WechatUtil.sendNotice(stuOpenId, noticeDataVo);
        }
        log.info("{} - {} - 结束执行异步通知", studentId, jobId);
    }
}
