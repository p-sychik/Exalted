package com.exalted.exaltedapp.controller

import com.exalted.exaltedapp.data.SignupRequest
import com.exalted.exaltedapp.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController (
    private val authService: AuthService
) {
    @GetMapping("/status")
    fun index(): String {
        return "hello world"
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignupRequest): ResponseEntity<String> {
        authService.signUp(request.username, request.password)
        return ResponseEntity.ok("Signed up successfully.")
    }
}