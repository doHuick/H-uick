package com.huick.notificationservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huick.notificationservice.domain.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByUserId(Long userId);
}
