package com.dohit.huick.domain.banking.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}