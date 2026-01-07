package com.exalted.exaltedapp.function

import com.exalted.exaltedapp.data.Difficulty
import com.exalted.exaltedapp.data.Priority
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.SkillType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun makeToDoItem(entry: String, description: String, priority: Priority, difficulty: Difficulty, skill: SkillType): ToDoItem {
    val completed = false
    val id = Uuid.random().toString();

    return ToDoItem(id, entry, description, priority, difficulty, skill, completed)
}


