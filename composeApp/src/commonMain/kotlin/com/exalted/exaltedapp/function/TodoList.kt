package com.exalted.exaltedapp.function

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.SkillType
import kotlin.collections.plus
import kotlin.text.ifEmpty

@Composable
fun TodoList(){
    var showContent by remember { mutableStateOf(false) }
    var toDoList by remember { mutableStateOf(listOf<ToDoItem>()) }
    val completedTasks = toDoList.filter { it.completed }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { showContent = !showContent },

            ) {
            Text("Create a task...", fontSize = 24.sp)
        }
        val entryState = rememberTextFieldState()
        val descriptionState = rememberTextFieldState()

        AnimatedVisibility(showContent) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var priority by remember { mutableStateOf("") }
                var difficulty by remember { mutableStateOf("") }
                var skill by remember { mutableStateOf<SkillType?>(null) }
                var expandedPriority by remember { mutableStateOf(false) }
                var expandedDifficulty by remember { mutableStateOf(false) }
                var expandedSkill by remember { mutableStateOf(false) }

                val canCreateTodo by remember {
                    derivedStateOf {
                        entryState.text.isNotBlank() &&
                                priority.isNotBlank() &&
                                difficulty.isNotBlank() &&
                                skill != null
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            state = entryState,
                            label = { Text("Task") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        TextField(
                            state = descriptionState,
                            label = { Text("Description (optional)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = { expandedPriority = !expandedPriority },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(priority.ifEmpty { "Priority" }, fontSize = 24.sp)
                            }
                            DropdownMenu(
                                expanded = expandedPriority,
                                onDismissRequest = { expandedPriority = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Low", fontSize = 24.sp) },
                                    onClick = {priority = "Low"; expandedPriority = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("Medium", fontSize = 24.sp) },
                                    onClick = {priority = "Medium"; expandedPriority = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("High", fontSize = 24.sp) },
                                    onClick = {priority = "High"; expandedPriority = false}
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = { expandedDifficulty = !expandedDifficulty },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(difficulty.ifEmpty { "Difficulty" }, fontSize = 24.sp)
                            }
                            DropdownMenu(
                                expanded = expandedDifficulty,
                                onDismissRequest = { expandedDifficulty = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Easy", fontSize = 24.sp) },
                                    onClick = {difficulty = "Easy"; expandedDifficulty = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("Moderate", fontSize = 24.sp) },
                                    onClick = {difficulty = "Moderate"; expandedDifficulty = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("Hard", fontSize = 24.sp) },
                                    onClick = {difficulty = "Hard"; expandedDifficulty = false}
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = { expandedSkill = !expandedSkill },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = skill?.displayName ?: "Skill",
                                    fontSize = 24.sp
                                )
                            }
                            DropdownMenu(
                                expanded = expandedSkill,
                                onDismissRequest = { expandedSkill = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Alchemy", fontSize = 24.sp) },
                                    onClick = {skill = SkillType.ALCHEMY; expandedSkill = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("Craftsmanship", fontSize = 24.sp) },
                                    onClick = {skill = SkillType.CRAFTSMANSHIP; expandedSkill = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("Scholarship", fontSize = 24.sp) },
                                    onClick = {skill = SkillType.SCHOLARSHIP; expandedSkill = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("Vigor", fontSize = 24.sp) },
                                    onClick = {skill = SkillType.VIGOR; expandedSkill = false}
                                )
                                DropdownMenuItem(
                                    text = { Text("Eloquence", fontSize = 24.sp) },
                                    onClick = {skill = SkillType.ELOQUENCE; expandedSkill = false}
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    toDoList = toDoList + makeToDoItem(
                                        entryState.text.toString(),
                                        descriptionState.text.toString(),
                                        priority,
                                        difficulty,
                                        skill!!
                                    )
                                    entryState.clearText()
                                    descriptionState.clearText()
                                    priority = ""
                                    difficulty = ""
                                    skill = null
                                },
                                enabled = canCreateTodo,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Text("Inscribe", fontSize = 24.sp)
                            }
                        }
                    }
                }
                val listState = rememberLazyListState()
                AnimatedVisibility(
                    visible = toDoList.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxWidth(),

                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            items(
                                items = toDoList.sortedByDescending { priorityWeight(it.priority) },
                                key = { it.id }
                            ) { item ->
                                TodoRow(
                                    item = item,
                                    onToggleCompleted = { toggled ->
                                        toDoList = toDoList.map {
                                            if (it.id == toggled.id) it.copy(completed = !it.completed) else it
                                        }
                                    },
                                    onAutoRemove = { removed ->
                                        toDoList = toDoList.filterNot { it.id == removed.id }
                                    }
                                )
                                HorizontalDivider(
                                    color = Color.Gray.copy(alpha = 0.5f),
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}