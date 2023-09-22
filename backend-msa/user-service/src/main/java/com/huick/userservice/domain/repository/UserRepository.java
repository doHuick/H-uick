package com.huick.userservice.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huick.userservice.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findBySocialId(String socialId);

	Optional<User> findByUserId(Long userId);
}
