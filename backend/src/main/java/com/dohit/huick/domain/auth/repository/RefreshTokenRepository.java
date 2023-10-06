package com.dohit.huick.domain.auth.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.dohit.huick.domain.auth.entity.RefreshToken;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
	private final RedisTemplate redisTemplate;

	public void save(RefreshToken refreshToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(String.valueOf(refreshToken.getUserId()), refreshToken.getRefreshToken());
		redisTemplate.expire(refreshToken.getRefreshToken(), 7, TimeUnit.SECONDS);
	}

	public Optional<RefreshToken> findByUserId(Long userId) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		RefreshToken refreshToken = RefreshToken.of(userId, valueOperations.get(String.valueOf(userId)));

		if (Objects.isNull(refreshToken)) {
			return Optional.empty();
		}
		return Optional.of(refreshToken);
	}

	public void updateRefreshToken(Long userId, RefreshToken newRefreshToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(String.valueOf(userId), newRefreshToken.getRefreshToken());
		redisTemplate.expire(String.valueOf(userId), 7, TimeUnit.DAYS);
	}
}
