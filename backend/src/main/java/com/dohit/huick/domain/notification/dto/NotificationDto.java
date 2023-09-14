package com.dohit.huick.domain.notification.dto;

import com.dohit.huick.api.notification.dto.NotificationApiDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDto {
	private Long userId;
	private String token;
	private String title;
	private String body;

	@Builder
	private NotificationDto(Long userId, String token, String title, String body) {
		this.userId = userId;
		this.token = token;
		this.title = title;
		this.body = body;
	}

	static public NotificationDto from(NotificationApiDto.Request request) {
		return NotificationDto.builder()
			.userId(request.getUserId())
			.token(request.getToken())
			.title(request.getTitle())
			.body(request.getBody())
			.build();
	}
}
