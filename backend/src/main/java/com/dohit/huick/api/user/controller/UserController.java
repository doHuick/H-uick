package com.dohit.huick.api.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.user.dto.UserApiDto;
import com.dohit.huick.domain.user.dto.UserDto;
import com.dohit.huick.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/rrn")
	public ResponseEntity<?> signup(@RequestBody UserApiDto.Request request) {
		userService.signup(UserDto.from(request));
		
		return ResponseEntity.ok().build();
	}

}
