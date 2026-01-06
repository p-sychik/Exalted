package com.exalted.exaltedapp.function

import com.exalted.exaltedapp.data.ToDoItem
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
fun MakeToDoItem(entry: String, description: String, priority: String, difficulty: String): ToDoItem {
    val completed = false
    val id = Uuid.random().toString();

    return ToDoItem(id, entry, description, priority, difficulty, completed)
}