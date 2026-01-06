package com.exalted.exaltedapp.data.progression

data class User (
    val username: String,
    val mainLevel: LevelClass,
    val skills: Map<SkillType, Skill>
    )