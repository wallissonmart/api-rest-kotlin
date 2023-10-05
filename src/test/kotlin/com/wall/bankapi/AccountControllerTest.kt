package com.wall.bankapi

import com.fasterxml.jackson.databind.ObjectMapper
import com.wall.bankapi.model.Account
import com.wall.bankapi.repository.AccountRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Test
    fun `test find all`() {
        accountRepository.save(Account(name = "João Teste", document = "123", phone = "99998888"))

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].document").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].phone").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`() {
        val account = accountRepository.save(Account(name = "João Teste", document = "123", phone = "99998888"))

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/id/${account.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by name`() {
        val account1 = accountRepository.save(Account(name = "João Carlos", document = "123", phone = "99998888"))
        val account2 = accountRepository.save(Account(name = "Raimundo", document = "123", phone = "99998888"))
        val account3 = accountRepository.save(Account(name = "Jonas Pereira", document = "123", phone = "99998888"))
        val nameSearch = "jo"

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/accounts/name/$nameSearch"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val responseBody = result.response.getContentAsString(Charsets.UTF_8)

        Assertions.assertTrue(responseBody.contains("João Carlos"))
        Assertions.assertTrue(responseBody.contains("Jonas Pereira"))

        Assertions.assertFalse(responseBody.contains("Raimundo"))
    }

    @Test
    fun `test create account`() {
        val account = Account(name = "Flávio", document = "12345", phone = "99998888")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts").contentType(MediaType.APPLICATION_JSON).content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(accountRepository.findAll().isEmpty())
    }

    @Test
    fun `test update account`() {
        val account = accountRepository
            .save(Account(name = "João Teste", document = "123", phone = "77774444"))
            .copy(name = "Updated")
        val json = ObjectMapper().writeValueAsString(account)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/accounts/${account.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andDo(MockMvcResultHandlers.print())

        val findById = accountRepository.findById(account.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(account.name, findById.get().name)
    }

    @Test
    fun `test delete account`() {
        val account = accountRepository.save(Account(name = "Teste", document = "123", phone = "77774444"))

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/accounts/${account.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        val findById = accountRepository.findById(account.id!!)
        Assertions.assertFalse(findById.isPresent)
    }

    @Test
    fun `test create account validation error name should be 5 character`() {
        val account = Account(name = "test", document = "123", phone = "987654321")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[nome] deve ter pelo menos 5 caracteres!"))
            .andDo(MockMvcResultHandlers.print())
    }
}