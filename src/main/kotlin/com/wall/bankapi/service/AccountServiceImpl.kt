package com.wall.bankapi.service

import com.wall.bankapi.model.Account
import com.wall.bankapi.repository.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class AccountServiceImpl(private val repository: AccountRepository) : AccountService {
    override fun create(account: Account): Account {
        if (account.name.isEmpty() || account.name.length < 5) {
            throw IllegalArgumentException("[nome] deve ter pelo menos 5 caracteres!")
        }

        return repository.save(account)
    }

    override fun getAll(): List<Account> {
        return repository.findAll()
    }


    override fun getById(id: Long): Optional<Account> {
        return repository.findById(id)
    }

    override fun getByName(name: String): List<Account>{
        return repository.findAll()
    }

    override fun update(id: Long, account: Account): Optional<Account> {
        val optional = getById(id)
        if (optional.isEmpty) Optional.empty<Account>()

        return optional.map {
            val accountToUpdate = it.copy(
                name = account.name,
                document = account.document,
                phone = account.phone
            )
            repository.save(accountToUpdate)
        }
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("Id not found $id") }
    }
}