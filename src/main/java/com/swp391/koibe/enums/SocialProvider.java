package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialProvider {

    GOOGLE("google"),
    FACEBOOK("facebook");

    private final String provider;

}
