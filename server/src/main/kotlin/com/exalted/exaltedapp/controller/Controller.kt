package com.exalted.exaltedapp.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("/status")
    fun index(): String {
        return "hello world"
    }
}