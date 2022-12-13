package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

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
    private Map<Object, Object> name1;

    /**
     * 预约老师
     */
    private Map<Object, Object> name10;

    /**
     * 预约时间段
     */
    private Map<Object, Object> time60;

    /**
     * 预约状态
     */
    private Map<Object, Object> phrase14;

    /**
     * 备注
     */
    private Map<Object, Object> thing7;
}
