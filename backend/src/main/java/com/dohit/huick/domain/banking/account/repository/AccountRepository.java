package com.dohit.huick.domain.banking.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByUserId(Long userId);
}