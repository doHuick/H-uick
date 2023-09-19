package com.dohit.huick.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
