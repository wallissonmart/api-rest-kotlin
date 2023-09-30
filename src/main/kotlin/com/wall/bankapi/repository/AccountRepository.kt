package com.wall.bankapi.repository

import com.wall.bankapi.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AccountRepository: JpaRepository<Account, Long> {
    //@Query("SELECT a FROM accounts a WHERE a.name LIKE %:name%")
    @Query("SELECT a FROM accounts a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    fun findByNameContaining(name: String): List<Account>
}