package com.zksy.reservationsystem.util.time;

import com.zksy.reservationsystem.util.auth.WechatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * access_token定时任务
 *
 * @author kkkoke
 * @since 2022/12/13
 */
@Slf4j
@Component
public class WechatAccessTokenTimeTask {

    private static String accessToken = "";

    @Scheduled(fixedRate = 5400000)
    public static void generateFaceAuthAccessToken() {
        log.info("定时任务开始执行");
        cleanAccessToken();
        accessToken = WechatUtil.getAccessToken();
        log.info("定时任务执行结束");
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void cleanAccessToken() {
        accessToken = "";
    }
}
