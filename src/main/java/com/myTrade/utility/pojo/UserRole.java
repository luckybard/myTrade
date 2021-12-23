package com.myTrade.utility.pojo;

import java.util.Set;

import static com.myTrade.utility.pojo.UserPermission.*;

public enum UserRole {
    USER(Set.of(AD_READ, AD_WRITE, CONVERSATION_READ, MESSAGE_WRITE,USER_WRITE, USER_READ)),
    ADMIN(Set.of(USER_WRITE, USER_READ, AD_READ, AD_WRITE));

    private final Set<UserPermission> userPermissionsSet;

    UserRole(Set<UserPermission> userPermissionsSet) {
        this.userPermissionsSet = userPermissionsSet;
    }

    public Set<UserPermission> getUserPermissionsSet() {
        return userPermissionsSet;
    }
}
