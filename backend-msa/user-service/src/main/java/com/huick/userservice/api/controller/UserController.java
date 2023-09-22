package com.huick.userservice.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huick.userservice.api.dto.UserApiDto;
import com.huick.userservice.domain.dto.UserDto;
import com.huick.userservice.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<String> testUser(){
		return ResponseEntity.ok("this is user service");
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserApiDto.Response> getUserByUserId(@PathVariable Long userId) {
		UserApiDto.Response response = UserApiDto.Response.from(userService.getUserByUserId(userId));
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/rrn/{userId}")
	public ResponseEntity<Void> signup(@PathVariable Long userId, @RequestBody UserApiDto.Request request) {
		userService.signup(UserDto.of(userId, request));

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/me")
	public ResponseEntity<Void> withdraw() {
		// userService.withdraw();

		return ResponseEntity.ok().build();
	}
}
