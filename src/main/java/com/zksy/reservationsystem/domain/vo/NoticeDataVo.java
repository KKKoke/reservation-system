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
     * 字段值例如：keyword1：订单类型，keyword2：下单金额，keyword3：配送地址，keyword4：取件地址，keyword5备注
     */
    private String value;
}
