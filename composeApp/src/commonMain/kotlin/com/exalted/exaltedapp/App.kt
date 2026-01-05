package com.exalted.exaltedapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.ToDoItem
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.exalted.exaltedapp.service.makeToDoItem
import kotlinx.coroutines.delay
import com.exalted.exaltedapp.ui.LightColors
import ui.theme.exaltedTypography

@Composable
@Preview
fun App() {
    MaterialTheme (
        colorScheme = LightColors,
        typography = exaltedTypography()
    ) {
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
                    var expandedPriority by remember { mutableStateOf(false) }
                    var expandedDifficulty by remember { mutableStateOf(false) }
                    val canCreateTodo by remember {
                        derivedStateOf {
                            entryState.text.isNotBlank() &&
                                    priority.isNotBlank() &&
                                    difficulty.isNotBlank()
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
                                    onClick = {
                                        toDoList = toDoList + makeToDoItem(
                                            entryState.text.toString(),
                                            descriptionState.text.toString(),
                                            priority,
                                            difficulty
                                        )
                                        entryState.clearText()
                                        descriptionState.clearText()
                                        priority = ""
                                        difficulty = ""
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
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(0.dp)
                            ) {
                                items(toDoList.sortedByDescending { priorityWeight(it.priority) }) { item ->
                                    ToDoRow(
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
}

@Composable
fun ToDoRow(
    item: ToDoItem,
    onToggleCompleted: (ToDoItem) -> Unit,
    onAutoRemove: (ToDoItem) -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(item.completed) {
        if (item.completed) {
            delay(2000)
            visible = false
            delay(300)
            onAutoRemove(item)
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (item.completed) Color(0xFFD6FFD6) else MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.entry, style = MaterialTheme.typography.titleMedium, fontSize = 30.sp)
                        if (item.description.isNotEmpty()) {
                            Text(text = item.description)
                        }
                        Text(
                            text = "Priority: ${item.priority} | Difficulty: ${item.difficulty}",

                        )
                    }
                    Button(
                        onClick = { onToggleCompleted(item) },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(if (item.completed) "Undo" else "Complete", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

fun priorityWeight(priority: String): Int = when (priority) {
    "High" -> 3
    "Medium" -> 2
    "Low" -> 1
    else -> 0
}

