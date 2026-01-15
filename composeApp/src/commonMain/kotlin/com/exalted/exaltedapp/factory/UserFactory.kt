package com.exalted.exaltedapp.factory

import com.exalted.exaltedapp.data.progression.LevelClass
import com.exalted.exaltedapp.data.progression.Skill
import com.exalted.exaltedapp.data.progression.SkillType
import com.exalted.exaltedapp.data.progression.User
import com.exalted.exaltedapp.function.xpToNextLevel

fun createGuestUser(): User {
    val startingLevel = 1

    val mainLevel = LevelClass(
        level = 1,
        xp = 0,
        xpToNext = xpToNextLevel(startingLevel)
    )

    val skills = SkillType.entries.associateWith { skillType ->
        Skill(
            type = skillType,
            level = LevelClass(
                level = startingLevel,
                xp = 0,
                xpToNext = xpToNextLevel(startingLevel)
            )
        )
    }

    return User(
        username = "Guest",
        mainLevel = mainLevel,
        skills = skills
    )
}

