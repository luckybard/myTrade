package com.myTrade.utility.pojo;

public enum UserPermission {
    AD_WRITE("ad:write"),
    AD_READ("ad:read"),
    USER_WRITE("user:write"),
    USER_READ("user:read"),
    MESSAGE_WRITE("message:write"),
    CONVERSATION_READ("conversation:read");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
