package com.zksy.reservationsystem.util.holder;

import com.ihbut.checkinsystemnew.domain.po.AdminInfo;

public class AdminInfoHolder {

    private static final ThreadLocal<AdminInfo> tlAdminInfo = new ThreadLocal<>();

    public static void saveAdminInfo(AdminInfo adminInfo) {
        tlAdminInfo.set(adminInfo);
    }

    public static AdminInfo getAdminInfo() {
        return tlAdminInfo.get();
    }

    public static void removeAdminInfo() {
        tlAdminInfo.remove();
    }
}
