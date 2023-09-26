package com.huick.userservice.domain.entity;

import java.util.Map;

import com.huick.userservice.domain.constant.SocialType;
import com.huick.userservice.global.error.ErrorCode;
import com.huick.userservice.global.error.exception.AuthenticationException;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(SocialType socialType, Map<String, Object> attributes) {
		switch (socialType) {
			case KAKAO:
				return new KakaoOAuth2UserInfo(attributes);
			default:
				throw new AuthenticationException(ErrorCode.INVALID_SOCIAL_TYPE);
		}
	}
}

