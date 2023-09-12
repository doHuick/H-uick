package com.dohit.huick.domain.user.entity;

import java.util.Map;

import com.dohit.huick.domain.auth.constant.SocialType;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.AuthenticationException;

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

