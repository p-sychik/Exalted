package com.exalted.exaltedapp.data.progression

data class LevelClass (
    val level: Int,
    val xp: Int,
    val xpToNext: Int
) {
    fun addXP(amount: Int): LevelClass {
        TODO()
    }

    fun calculateXP(amount: Int): Int {
        TODO()
    }
}
