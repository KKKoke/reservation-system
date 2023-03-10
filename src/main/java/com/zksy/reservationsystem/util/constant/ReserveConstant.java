package com.zksy.reservationsystem.util.constant;

/**
 * 访谈预约常量类
 *
 * @author kkkoke
 * @since 2022/11/22
 */
public class ReserveConstant {

    /** 访谈预约状态 待审核 */
    public static final Integer TO_BE_REVIEWED = 1;

    /** 访谈预约状态 已通过 */
    public static final Integer PASSED = 2;

    /** 访谈预约状态 未通过 */
    public static final Integer NOT_PASSED = 3;

    /** 访谈预约状态 已完成 */
    public static final Integer ENDED = 4;

    /** 访谈预约状态 已取消 */
    public static final Integer CANCELED = 5;
}
