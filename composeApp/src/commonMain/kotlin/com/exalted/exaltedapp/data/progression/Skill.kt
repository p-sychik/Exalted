package com.exalted.exaltedapp.data.progression

enum class SkillType (val displayName: String) {
    ALCHEMY("Alchemy"),
    CRAFTSMANSHIP("Craftsmanship"),
    SCHOLARSHIP("Scholarship"),
    VIGOR("Vigor"),
    ELOQUENCE("Eloquence"),
}

data class Skill(
    val type: SkillType,
    val level: LevelClass
)
