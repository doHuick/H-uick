package com.dohit.huick.global.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.auth.service.AuthService;
import com.dohit.huick.global.util.HeaderUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LogoutHandlerImpl implements LogoutHandler {

	private final AuthService authService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		authService.logout(HeaderUtil.getAccessToken(request));
	}
}
