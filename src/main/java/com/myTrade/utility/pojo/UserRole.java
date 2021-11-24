package com.myTrade.utility.pojo;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.myTrade.utility.pojo.UserPermission.*;

public enum UserRole {
    USER(Sets.newHashSet(AD_READ, AD_WRITE, CONVERSATION_READ, MESSAGE_WRITE)),
    ADMIN(Sets.newHashSet(USER_WRITE, USER_READ, AD_READ, AD_WRITE));

    private final Set<UserPermission> userPermissionsSet;

    UserRole(Set<UserPermission> userPermissionsSet) {
        this.userPermissionsSet = userPermissionsSet;
    }

    public Set<UserPermission> getUserPermissionsSet() {
        return userPermissionsSet;
    }
}
