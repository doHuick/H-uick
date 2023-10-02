package com.dohit.huick.domain.user.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dohit.huick.domain.auth.entity.RefreshToken;
import com.dohit.huick.domain.auth.repository.RefreshTokenRepository;
import com.dohit.huick.domain.banking.service.BankingService;
import com.dohit.huick.domain.user.dto.UserDto;
import com.dohit.huick.domain.user.entity.User;
import com.dohit.huick.domain.user.repository.UserRepository;
import com.dohit.huick.domain.user.util.Base64DecodedMultipartFile;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.AuthenticationException;
import com.dohit.huick.global.error.exception.BusinessException;
import com.dohit.huick.global.token.AuthToken;
import com.dohit.huick.global.token.AuthTokenProvider;
import com.dohit.huick.global.vo.Image;
import com.dohit.huick.infra.aws.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthTokenProvider authTokenProvider;
	private final BankingService bankingService;

	private final S3Uploader s3Uploader;

	private final String USER_S3_DIRNAME = "user";

	@Transactional
	public void signup(UserDto userDto) throws BusinessException {
		User user = userRepository.findByUserId(userDto.getUserId())
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
		user.signup(userDto);
	}

	@Transactional
	public void withdraw() throws BusinessException {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (name == null) {
			throw new BusinessException(ErrorCode.NOT_EXIST_USER);
		}

		Long userId = Long.valueOf(name);
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
		user.withdraw();
		AuthToken refreshToken = authTokenProvider.createAuthToken(name, new Date(System.currentTimeMillis()));
		refreshTokenRepository.save(RefreshToken.of(Long.valueOf(name), refreshToken.getToken()));
	}

	public UserDto getUserByUserId(Long userId) {
		UserDto userDto = UserDto.from(userRepository.findByUserId(userId)
			.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_USER)));
		bankingService.getAccountByUserId(userId);

		return UserDto.of(userDto, bankingService.getAccountByUserId(userId));
	}

	public void updateSignature(Long userId, String signatureBase64) throws IOException {
		byte[] signatureBytes = decodeBase64Image(signatureBase64);
		MultipartFile signatureMultipart = new Base64DecodedMultipartFile(signatureBytes);
		final Image image = s3Uploader.uploadSignature(signatureMultipart, USER_S3_DIRNAME);
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_USER));
		user.updateSignatureUrl(image.getImageUrl());

		userRepository.save(user);
	}

	public byte[] decodeBase64Image(String signatureBase64) {
		if (signatureBase64 == null || signatureBase64.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid Base64 string");
		}

		// 데이터 URI 스키마 제거
		String base64EncodedData = signatureBase64.contains(",")
			? signatureBase64.split(",")[1]
			: signatureBase64;

		// Whitespace 제거
		base64EncodedData = base64EncodedData.replaceAll("\\s", "");

		// Base64 디코딩
		try {
			return javax.xml.bind.DatatypeConverter.parseBase64Binary(base64EncodedData);
		} catch (IllegalArgumentException e) {
			// 로그 출력
			System.err.println("Failed to decode Base64 string: " + base64EncodedData);
			throw new IllegalArgumentException("Failed to decode Base64 string", e);
		}
	}

	@Transactional
	public void updatePassword(UserDto userDto) throws BusinessException {
		User user = userRepository.findByUserId(userDto.getUserId())
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
		user.updatePassword(userDto.getPassword());
	}

}
