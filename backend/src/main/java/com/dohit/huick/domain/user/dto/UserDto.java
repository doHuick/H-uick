package com.dohit.huick.domain.user.dto;

import java.time.LocalDateTime;

import com.dohit.huick.api.user.dto.UserApiDto;
import com.dohit.huick.domain.auth.constant.Role;
import com.dohit.huick.domain.auth.constant.SocialType;
import com.dohit.huick.domain.banking.account.dto.AccountDto;
import com.dohit.huick.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

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
	private String phoneNumber;
	private String password;
	private LocalDateTime issueDate;
	private String walletKey;
	private AccountDto accountInfo;

	@Builder
	private UserDto(Long userId, String name, String rrn, String address, String walletAddress,
		SocialType socialType, String socialId, LocalDateTime createdTime, Role role,
		LocalDateTime withdrawalTime, String signatureUrl, String phoneNumber, String password,
		LocalDateTime issueDate, String walletKey, AccountDto accountInfo) {
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
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.issueDate = issueDate;
		this.walletKey = walletKey;
		this.accountInfo = accountInfo;
	}

	public static UserDto of(Long userId, UserApiDto.Request request) {
		return UserDto.builder()
			.userId(userId)
			.name(request.getName())
			.rrn(request.getRrn())
			.address(request.getAddress())
			.phoneNumber(request.getPhoneNumber())
			.walletAddress(request.getWalletAddress())
			.walletKey(request.getWalletKey())
			.build();
	}

	public static UserDto from(User user) {
		return UserDto.builder()
			.userId(user.getUserId())
			.name(user.getName())
			.rrn(user.getRrn())
			.address(user.getAddress())
			.walletAddress(user.getWalletAddress())
			.socialType(user.getSocialType())
			.socialId(user.getSocialId())
			.createdTime(user.getCreatedTime())
			.role(user.getRole())
			.withdrawalTime(user.getWithdrawalTime())
			.signatureUrl(user.getSignatureUrl())
			.issueDate(user.getIssueDate())
			.phoneNumber(user.getPhoneNumber())
			.walletKey(user.getWalletKey())
			.password(user.getPassword())
			.build();
	}

	public static UserDto of(UserDto userDto, AccountDto accountDto) {
		return UserDto.builder()
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
			.walletKey(userDto.getWalletKey())
			.accountInfo(accountDto)
			.build();
	}

	public static UserDto from(String name, String address) {
		return UserDto.builder()
			.name(name)
			.address(address)
			.build();
	}

	public static UserDto from(String name, String address, String rrn, String phoneNumber) {
		return UserDto.builder()
			.name(name)
			.address(address)
			.rrn(rrn)
			.phoneNumber(phoneNumber)
			.build();
	}

	public static UserDto of(Long userId, UserApiDto.PasswordRequest request) {
		return UserDto.builder()
			.userId(userId)
			.password(request.getPassword())
			.build();
	}
}
