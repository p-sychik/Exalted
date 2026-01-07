package com.exalted.exaltedapp.data

import com.exalted.exaltedapp.data.progression.SkillType

data class ToDoItem(
    val id: String,
    val entry: String,
    val description: String,
    val priority: Priority,
    val difficulty: Difficulty,
    val skill: SkillType,
    val completed: Boolean = false
)