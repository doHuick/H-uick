package com.dohit.huick.domain.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.notification.entity.DeviceToken;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
	List<DeviceToken> findByUserId(Long userId);
}
