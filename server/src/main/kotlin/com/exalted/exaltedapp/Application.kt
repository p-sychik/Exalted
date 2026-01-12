package com.exalted.exaltedapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration


@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class,
    ]
)

class ExaltedappApplication

fun main(args: Array<String>) {
	runApplication<ExaltedappApplication>(*args)
}
