package ru.inversionkavkaz.rupay.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.*
import ru.inversionkavkaz.rupay.data.RegisterRequest
import ru.inversionkavkaz.rupay.repository.RuPayRepository

@RestController
class RuPayObserverController(private val objectMapper: ObjectMapper, val ruPayRepository: RuPayRepository) {
    @GetMapping("card/{cardnum}")
    fun getCardInfo(@PathVariable cardnum: String) = ruPayRepository.getCardInfo(cardnum)

    @PostMapping("register")
    fun register(@RequestBody registerRequest: RegisterRequest) = ruPayRepository.register(registerRequest)
}