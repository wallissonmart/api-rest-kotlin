package com.wall.bankapi.repository

import com.wall.bankapi.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long> {
}