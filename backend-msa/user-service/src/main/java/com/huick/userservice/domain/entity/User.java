package com.huick.userservice.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.huick.userservice.domain.constant.Role;
import com.huick.userservice.domain.constant.SocialType;
import com.huick.userservice.domain.dto.UserDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
public class User {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(length = 512, unique = true)
	@Size(max = 512)
	private String walletAddress;

	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	@Column(length = 512, unique = true)
	@Size(max = 512)
	private String socialId;

	@CreatedDate
	private LocalDateTime createdTime;

	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column
	private LocalDateTime withdrawalTime;

	@Column
	private String signatureUrl;

	@Column(length = 13)
	@Size(min = 13, max = 13)
	private String rrn;

	@Column(length = 100)
	@Size(max = 100)
	private String address;

	@Column(length = 20)
	@Size(max = 20)
	private String name;

	@Column
	private LocalDateTime issueDate;

	@Builder
	public User(String walletAddress, SocialType socialType, String socialId,
		Role role, LocalDateTime withdrawalTime, String signatureUrl, String rrn,
		String address, String name, LocalDateTime issueDate) {
		this.walletAddress = walletAddress;
		this.socialType = socialType;
		this.socialId = socialId;
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
