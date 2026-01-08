package com.exalted.exaltedapp.data

enum class Difficulty(val displayName: String, val xpModifier: Double) {
    EASY("Easy", 0.5),
    MODERATE("Moderate", 1.3),
    HARD("Hard", 2.2)
}