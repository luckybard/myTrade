package com.myTrade.utility;

public enum UserPermission {
    AD_WRITE("ad:write"),
    AD_READ("ad:read"),
    USER_WRITE("user:write"),
    USER_READ("user:read");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
