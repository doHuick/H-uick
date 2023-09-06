package com.dohit.huick.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// 인증
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "401001", "토큰이 만료되었습니다."),
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "401002", "해당 토큰은 유효한 토큰이 아닙니다."),
	NOT_EXIST_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "401003", "Authorization Header가 빈값입니다."),
	NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "401004", "인증 타입이 Bearer 타입이 아닙니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401005", "해당 refresh token은 존재하지 않습니다."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "401006", "해당 refresh token은 만료되었습니다."),
	NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "401007", "해당 토큰은 Access Token이 아닙니다."),
	NOT_REFRESH_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "401008", "해당 토큰은 Refresh Token이 아닙니다."),

	// 인가
	FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "403001", "관리자 Role이 아닙니다."),
	NOT_EXIST_CALLBACK_TYPE(HttpStatus.FORBIDDEN, "403002", "잘못된 uri입니다."),

	// 회원
	INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "404001", "잘못된 회원 타입 입니다. (memberType : KAKAO)"),
	ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "404002", "이미 가입된 회원입니다."),
	NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "404003", "존재하지 않는 회원입니다.");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
}