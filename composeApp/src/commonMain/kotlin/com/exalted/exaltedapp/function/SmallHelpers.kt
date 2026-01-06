package com.exalted.exaltedapp.function

fun priorityWeight(priority: String): Int = when (priority) {
    "High" -> 3
    "Medium" -> 2
    "Low" -> 1
    else -> 0
}