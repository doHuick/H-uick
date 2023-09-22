package com.dohit.huick.api.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.auth.dto.AuthApiDto;
import com.dohit.huick.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@GetMapping("/refresh")
	public ResponseEntity<AuthApiDto.Response> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("refresh token");
		String token = authService.refreshToken(request, response);

		return ResponseEntity.ok().body(AuthApiDto.Response.from(token));
	}
}
