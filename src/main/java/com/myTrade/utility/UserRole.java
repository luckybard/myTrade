package com.myTrade.utility;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.myTrade.utility.UserPermission.*;

public enum UserRole {
    USER(Sets.newHashSet(AD_READ, AD_WRITE)),
    ADMIN(Sets.newHashSet(USER_WRITE, USER_READ, AD_READ, AD_WRITE));

    private final Set<UserPermission> userPermissionsSet;

    UserRole(Set<UserPermission> userPermissionsSet) {
        this.userPermissionsSet = userPermissionsSet;
    }

    public Set<UserPermission> getUserPermissionsSet() {
        return userPermissionsSet;
    }
}
