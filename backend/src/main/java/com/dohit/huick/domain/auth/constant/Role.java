package com.dohit.huick.domain.auth.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	USER("ROLE_USER", "일반 사용자 권한"),
	ADMIN("ROLE_ADMIN", "관리자 권한"),
	GUEST("GUEST", "게스트 권한");

	private final String code;
	private final String displayName;

	public static Role of(String code) {
		return Arrays.stream(Role.values())
			.filter(r -> r.getCode().equals(code))
			.findAny()
			.orElse(GUEST);
	}
}
