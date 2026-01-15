package com.exalted.exaltedapp.data.progression

import com.exalted.exaltedapp.function.xpToNextLevel

data class LevelClass (
    val level: Int,
    val xp: Int,
    val xpToNext: Int
) {
    fun addXP(amount: Int): LevelClass {
        var newXP = (xp + amount)
        var newLevel = level
        var xpNeeded = xpToNext

        while (newXP > xpNeeded) {
            newXP -= xpNeeded
            newLevel += 1
            xpNeeded = xpToNextLevel(newLevel)
        }

        return copy(level = newLevel, xp = newXP, xpToNext = xpNeeded)
    }
}
