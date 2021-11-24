package com.myTrade.utility;

import com.myTrade.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtility {
    public static final String ANONYMOUS_USER_USERNAME = "anonymousUser";
    public static final Integer POINTS_COST_OF_HIGHLIGHTING_AD = 1;
    public static final Integer INITIAL_HIGHLIGHT_POINTS = 5;

    public static String getUsernameFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean checkIfUserHaveEnoughHighlightPoints(UserEntity userEntity) {
        return userEntity.getHighlightPoints() >= POINTS_COST_OF_HIGHLIGHTING_AD;
    }

    public static boolean isUserAnonymous() {
        return getUsernameFromContext().equals(ANONYMOUS_USER_USERNAME);
    }
}
