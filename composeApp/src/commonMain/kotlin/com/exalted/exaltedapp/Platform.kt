package com.exalted.exaltedapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform