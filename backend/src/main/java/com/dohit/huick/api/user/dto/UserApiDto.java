package com.dohit.huick.api.user.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.auth.constant.Role;
import com.dohit.huick.domain.auth.constant.SocialType;
import com.dohit.huick.domain.banking.account.dto.AccountDto;
import com.dohit.huick.domain.user.dto.UserDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

public class UserApiDto {

	@Getter
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		private String name;
		private String rrn;
		private String address;
		private String phoneNumber;
		private String walletAddress;
		private String walletKey;
	}

	@Getter
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class PasswordRequest {
		private String password;
	}

	@Getter
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private Long userId;
		private String name;
		private String rrn;
		private String address;
		private String walletAddress;
		private SocialType socialType;
		private String socialId;
		private LocalDateTime createdTime;
		private Role role;
		private LocalDateTime withdrawalTime;
		private String signatureUrl;
		private LocalDateTime issueDate;
		private String phoneNumber;
		private String password;
		private AccountDto accountInfo;

		@Builder
		private Response(Long userId, String name, String rrn, String address, String walletAddress,
			SocialType socialType,
			String socialId, LocalDateTime createdTime, Role role, LocalDateTime withdrawalTime, String signatureUrl,
			LocalDateTime issueDate, String phoneNumber, String password, AccountDto accountInfo) {
			this.userId = userId;
			this.name = name;
			this.rrn = rrn;
			this.address = address;
			this.walletAddress = walletAddress;
			this.socialType = socialType;
			this.socialId = socialId;
			this.createdTime = createdTime;
			this.role = role;
			this.withdrawalTime = withdrawalTime;
			this.signatureUrl = signatureUrl;
			this.issueDate = issueDate;
			this.phoneNumber = phoneNumber;
			this.password = password;
			this.accountInfo = accountInfo;
		}

		public static Response from(UserDto userDto) {
			return Response.builder()
				.userId(userDto.getUserId())
				.name(userDto.getName())
				.rrn(userDto.getRrn())
				.address(userDto.getAddress())
				.walletAddress(userDto.getWalletAddress())
				.socialType(userDto.getSocialType())
				.socialId(userDto.getSocialId())
				.createdTime(userDto.getCreatedTime())
				.role(userDto.getRole())
				.withdrawalTime(userDto.getWithdrawalTime())
				.signatureUrl(userDto.getSignatureUrl())
				.issueDate(userDto.getIssueDate())
				.phoneNumber(userDto.getPhoneNumber())
				.password(userDto.getPassword())
				.accountInfo(userDto.getAccountInfo())
				.build();
		}
	}

}
