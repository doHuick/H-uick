package com.dohit.huick.global.userinfo;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasUserInfoAnnotation = parameter.hasParameterAnnotation(UserInfo.class);
		boolean isLongAssignable = Long.class.isAssignableFrom(parameter.getParameterType());
		return hasUserInfoAnnotation && isLongAssignable;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = (Authentication)webRequest.getUserPrincipal();
		if (authentication != null) {
			User principal = (User)authentication.getPrincipal();
			if (principal != null) {
				return Long.valueOf(principal.getUsername()); // 사용자 이름을 반환
			}
		}
		return null;
	}
}
