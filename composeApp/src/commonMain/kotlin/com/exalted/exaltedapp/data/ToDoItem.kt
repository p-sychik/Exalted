package com.exalted.exaltedapp.data

data class ToDoItem(
    val id: String,
    val entry: String,
    val description: String,
    val priority: String,
    val difficulty: String,
    var completed: Boolean = false
)