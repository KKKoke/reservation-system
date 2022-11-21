package com.zksy.reservationsystem.util.common;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.exception.BizException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换器
 *
 * @author kkkoke
 * @since 2022/11/21
 */
public class TimeConvertor {

    /**
     * 将一个数值转化为一天的时分时间
     */
    public static String getHM(Long value) {
        String hour = "00";
        String minute = "00";
        String second = "00";
        if (value != null) {
            long v = value / 1000;
            hour = v / 3600 + "";  // 获得小时
            minute = v % 3600 / 60 + "";  // 获得分钟
        }
        return (hour.length() > 1 ? hour : "0" + hour) + ":" + (minute.length() > 1 ? minute : "0" + minute);
    }

    /**
     * 将一天的时分时间转化为数值
     */
    public static Long getValue(String dayTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = dateFormat.parse(dayTime);
            String[] timeSplit = dayTime.split(":");
            System.out.println(timeSplit[0]);
            int hour = Integer.parseInt(timeSplit[0]);
            int minute = Integer.parseInt(timeSplit[1]);
            return (hour * 60L + minute) * 60 * 1000L;
        } catch (ParseException e) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "日期格式不符合");
        }
    }


}
