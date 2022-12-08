package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信通知内容模版
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDataVo {

    /**
     * 预约人
     */
    private String name1;

    /**
     * 预约老师
     */
    private String name10;

    /**
     * 预约时间段
     */
    private String time60;

    /**
     * 预约状态
     */
    private String phrase14;

    /**
     * 备注
     */
    private String thing7;
}
