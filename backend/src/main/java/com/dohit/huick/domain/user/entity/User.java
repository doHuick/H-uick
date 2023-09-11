package com.dohit.huick.domain.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.dohit.huick.domain.auth.constant.Role;
import com.dohit.huick.domain.auth.constant.SocialType;
import com.dohit.huick.domain.user.dto.UserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "wallet_address", length = 512, unique = true)
	@Size(max = 512)
	private String walletAddress;

	@Column(name = "social_type", length = 20)
	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	@Column(name = "social_id", length = 512, unique = true)
	@Size(max = 512)
	private String socialId;

	@Column(name = "created_time")
	private LocalDateTime createdTime;

	@Column(name = "role", length = 20)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "withdrawal_time")
	private LocalDateTime withdrawalTime;

	@Column(name = "signature_url")
	private String signatureUrl;

	@Column(name = "rrn", length = 13)
	@Size(min = 13, max = 13)
	private String rrn;

	@Column(name = "address", length = 100)
	@Size(max = 100)
	private String address;

	@Column(name = "name", length = 20)
	@Size(max = 20)
	private String name;

	@Column(name = "issue_date")
	private LocalDateTime issueDate;

	@Builder
	public User(String walletAddress, SocialType socialType, String socialId,
		LocalDateTime createdTime, Role role, LocalDateTime withdrawalTime, String signatureUrl, String rrn,
		String address, String name, LocalDateTime issueDate) {
		this.walletAddress = walletAddress;
		this.socialType = socialType;
		this.socialId = socialId;
		this.createdTime = createdTime;
		this.role = role;
		this.withdrawalTime = withdrawalTime;
		this.signatureUrl = signatureUrl;
		this.rrn = rrn;
		this.address = address;
		this.name = name;
		this.issueDate = issueDate;
	}

	public void signup(UserDto userDto) {
		this.address = userDto.getAddress();
		this.name = userDto.getName();
		this.rrn = userDto.getRrn();
	}

	public void withdraw() {
		this.walletAddress = null;
		this.rrn = null;
		this.address = null;
		this.name = null;
		this.issueDate = null;
		this.withdrawalTime = LocalDateTime.now().plusMonths(1);
	}

}
