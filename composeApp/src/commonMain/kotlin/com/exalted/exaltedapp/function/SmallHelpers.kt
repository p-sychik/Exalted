package com.exalted.exaltedapp.function

import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.SkillType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun priorityWeight(priority: String): Int = when (priority) {
    "High" -> 3
    "Medium" -> 2
    "Low" -> 1
    else -> 0
}

@OptIn(ExperimentalUuidApi::class)
fun makeToDoItem(entry: String, description: String, priority: String, difficulty: String, skill: SkillType): ToDoItem {
    val completed = false
    val id = Uuid.random().toString();

    return ToDoItem(id, entry, description, priority, difficulty, skill, completed)
}