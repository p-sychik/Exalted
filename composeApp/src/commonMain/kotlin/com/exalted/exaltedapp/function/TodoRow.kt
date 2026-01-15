package com.exalted.exaltedapp.function

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.User

@Composable
fun TodoRow(
    item: ToDoItem,
    onToggleCompleted: (ToDoItem) -> Unit,
    onAutoRemove: (ToDoItem) -> Unit,
    user: User,
    onUserUpdate: (User) -> Unit
) {
    val visible = remember(item.id) { mutableStateOf(true) }

    LaunchedEffect(item.completed) {
        if (item.completed && visible.value) {
            visible.value = false

            onUserUpdate(user.copy(
                mainLevel = user.mainLevel.addXP(item.xpOnCompletion)
            ))

            onAutoRemove(item)
        }
    }

    AnimatedVisibility(
        visible = visible.value,
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
                        Text(text = item.entry,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 30.sp
                        )
                        if (item.description.isNotEmpty()) {
                            Text(text = item.description)
                        }
                        Text(
                            text = "Priority: ${item.priority.displayName} | Difficulty: ${item.difficulty.displayName} | Skill: ${item.skill.displayName} | XP: ${item.xpOnCompletion}",
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