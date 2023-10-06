package com.dohit.huick.api.user.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.user.dto.UserApiDto;
import com.dohit.huick.domain.user.dto.UserDto;
import com.dohit.huick.domain.user.service.UserService;
import com.dohit.huick.global.userinfo.UserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserApiDto.Response> getUserByUserId(@UserInfo Long userId) {
		UserApiDto.Response response = UserApiDto.Response.from(userService.getUserByUserId(userId));

		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/me")
	public ResponseEntity<Void> updatePassword(@UserInfo Long userId,
		@RequestBody UserApiDto.PasswordRequest request) {
		userService.updatePassword(UserDto.of(userId, request));

		return ResponseEntity.ok().build();
	}

	@PostMapping("/rrn")
	public ResponseEntity<Void> signup(@UserInfo Long userId, @RequestBody UserApiDto.Request request) {
		userService.signup(UserDto.of(userId, request));

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/signature")
	public ResponseEntity<Void> updateSignature(@UserInfo Long userId, @RequestBody String signatureBase64) throws
		IOException {
		userService.updateSignature(userId, signatureBase64);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/me")
	public ResponseEntity<Void> withdraw() {
		userService.withdraw();

		return ResponseEntity.ok().build();
	}

}
