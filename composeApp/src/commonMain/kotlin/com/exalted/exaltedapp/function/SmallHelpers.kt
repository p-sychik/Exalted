package com.exalted.exaltedapp.function

import com.exalted.exaltedapp.data.Difficulty
import com.exalted.exaltedapp.data.Priority
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.SkillType
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun makeToDoItem(entry: String, description: String, priority: Priority, difficulty: Difficulty, skill: SkillType): ToDoItem {
    val completed = false
    val id = Uuid.random().toString();

    val xpOnCompletion = calculateXP(priority, difficulty)

    return ToDoItem(id, entry, description, priority, difficulty, skill, completed, xpOnCompletion)
}

// Had an idea for skill points, one could be increasing your luck of doubling XP on task completion?
fun calculateXP(priority: Priority, difficulty: Difficulty): Int {
    val baseXP = (125..150).random()
    val xp : Int = (baseXP * priority.xpModifier * difficulty.xpModifier).roundToInt()

    return xp
}

suspend fun signUp(username: String, password: String): Boolean {
    //TODO
    return true
}


