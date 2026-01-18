 package com.exalted.exaltedapp.function

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.Difficulty
import com.exalted.exaltedapp.data.Priority
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.SkillType
import com.exalted.exaltedapp.data.progression.User
import com.exalted.exaltedapp.ui.StyledCard
import kotlin.collections.plus

@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    toDoList: List<ToDoItem>,
    onUpdate: (List<ToDoItem>) -> Unit,
    user: User,
    onUserUpdate: (User) -> Unit
){
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var task by rememberSaveable { mutableStateOf("") }
            var description by rememberSaveable { mutableStateOf("") }
            var difficulty by remember { mutableStateOf<Difficulty?>(Difficulty.EASY) }
            var priority by remember { mutableStateOf<Priority?>(Priority.LOW) }
            var skill by remember { mutableStateOf<SkillType?>(null) }
            var expandedPriority by remember { mutableStateOf(false) }
            var expandedDifficulty by remember { mutableStateOf(false) }
            var expandedSkill by remember { mutableStateOf(false) }

            val dropdownTextStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)

            val canCreateTodo by remember {
                derivedStateOf {
                    task.isNotBlank() &&
                            priority != null &&
                            difficulty != null &&
                            skill != null
                }
            }
            StyledCard {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create a task...",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                    OutlinedTextField(
                        value = task,
                        onValueChange = { if (it.length <= 80) task = it },
                        label = { Text("Task") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),

                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { if (it.length <= 1000) description = it },
                        label = { Text("Description (optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    ) {
                        Button(
                            onClick = { expandedPriority = !expandedPriority },
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text(
                                text = "Priority: " + priority?.displayName,
                                fontSize = 24.sp
                            )
                            DropdownMenu(
                                expanded = expandedPriority,
                                onDismissRequest = { expandedPriority = false },
                            ) {
                                Priority.entries.forEach { priorityType ->
                                    DropdownMenuItem(
                                        text = { Text(priorityType.displayName, style = dropdownTextStyle) },
                                        onClick = {
                                            priority = priorityType
                                            expandedPriority = false
                                        }
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { expandedDifficulty = !expandedDifficulty },
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text(
                                text = "Difficulty: " + difficulty?.displayName,
                                fontSize = 24.sp
                            )
                            DropdownMenu(
                                expanded = expandedDifficulty,
                                onDismissRequest = { expandedDifficulty = false },
                            ) {
                                Difficulty.entries.forEach { difficultyType ->
                                    DropdownMenuItem(
                                        text = { Text(difficultyType.displayName, style = dropdownTextStyle) },
                                        onClick = {
                                            difficulty = difficultyType
                                            expandedDifficulty = false
                                        }
                                    )
                                }
                            }
                        }
                        Button(
                            onClick = { expandedSkill = !expandedSkill },
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text(
                                text = skill?.displayName ?: "Skill",
                                fontSize = 24.sp
                            )
                            DropdownMenu(
                                expanded = expandedSkill,
                                onDismissRequest = { expandedSkill = false },
                            ) {
                                SkillType.entries.forEach { skillType ->
                                    DropdownMenuItem(
                                        text = { Text(skillType.displayName, style = dropdownTextStyle) },
                                        onClick = {
                                            skill = skillType
                                            expandedSkill = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val newItem = makeToDoItem(
                                task,
                                description,
                                priority!!,
                                difficulty!!,
                                skill!!
                            )
                            onUpdate(toDoList + newItem)

                            task = ""
                            description = ""
                            priority = Priority.LOW
                            difficulty = Difficulty.EASY
                            skill = null
                        },
                        enabled = canCreateTodo,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    ) {
                        Text("Inscribe", fontSize = 24.sp)
                    }
                }
            }
            val listState = rememberLazyListState()
            AnimatedVisibility(
                visible = toDoList.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                StyledCard {

                    val sortedList = toDoList.sortedByDescending { it.priority.weight }

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxWidth(),

                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(
                            items = sortedList,
                            key = { it.id }
                        ) { item ->
                            TodoRow(
                                item = item,
                                onToggleCompleted = { toggled ->
                                    val newList = toDoList.map {
                                        if (it.id == toggled.id) it.copy(completed = !it.completed)
                                        else it
                                    }
                                    onUpdate(newList)
                                },
                                onAutoRemove = { removed ->
                                    val newList = toDoList.filterNot { it.id == removed.id }
                                    onUpdate(newList)
                                },
                                user = user,
                                onUserUpdate = onUserUpdate
                            )
                            HorizontalDivider(
                                color = Color.Black.copy(alpha = 0.5f),
                                thickness = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

