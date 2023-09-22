package com.huick.notificationservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huick.notificationservice.domain.entity.DeviceToken;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
	List<DeviceToken> findByUserId(Long userId);
}
