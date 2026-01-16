package com.exalted.exaltedapp.function

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.exalted.exaltedapp.data.Priority
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.SkillType
import com.exalted.exaltedapp.data.progression.User
import exaltedapp.composeapp.generated.resources.Res
import exaltedapp.composeapp.generated.resources.banner
import exaltedapp.composeapp.generated.resources.character
import org.jetbrains.compose.resources.painterResource

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

            val updatedLevel = user.mainLevel.addXP(item.xpOnCompletion)

            val updatedSkills = user.skills.toMutableMap().apply {
                this[item.skill]?.let { currentSkill ->
                    this[item.skill] = currentSkill.copy(
                        level = currentSkill.level.addXP(item.xpOnCompletion)
                    )
                }
            }

            onUserUpdate(user.copy(
                mainLevel = updatedLevel,
                skills = updatedSkills
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
            if (item.priority == Priority.HIGH) {
                Image(
                    painter = painterResource(Res.drawable.banner),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(60.dp)
                        .align(Alignment.TopStart)
                        .offset(x = (-40).dp, y = (-22).dp)
                        .zIndex(1f)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(0, 0) {
                                placeable.place(0, 0)
                            }
                        }
                )
            }
            Column {
                val bannerOffset = if (item.priority == Priority.HIGH) 50.dp else 0.dp

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.entry,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(start = bannerOffset),
                            overflow = TextOverflow.Ellipsis
                        )
                        if (item.description.isNotEmpty()) {
                            Text(
                                text = item.description,
                                maxLines = 1,
                                modifier = Modifier.padding(start = bannerOffset),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Text(
                            text = "Priority: ${item.priority.displayName} | Difficulty: ${item.difficulty.displayName} | Skill: ${item.skill.displayName} | XP: ${item.xpOnCompletion}",
                            modifier = Modifier.padding(start = bannerOffset)
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