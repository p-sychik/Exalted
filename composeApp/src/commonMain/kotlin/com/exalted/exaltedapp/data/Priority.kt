package com.exalted.exaltedapp.data

enum class Priority(val displayName: String, val weight: Int, val xpModifier: Double) {
    LOW("Low", 1, 0.9),
    MEDIUM("Medium", 2, 1.1),
    HIGH("High", 3, 1.2),
}