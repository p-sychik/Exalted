package com.exalted.exaltedapp.function

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.exalted.exaltedapp.data.Priority
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.SkillType
import com.exalted.exaltedapp.data.progression.User
import exaltedapp.composeapp.generated.resources.Res
import exaltedapp.composeapp.generated.resources.banner
import exaltedapp.composeapp.generated.resources.strikethrough
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import kotlin.collections.set

@Composable
fun TodoRow(
    item: ToDoItem,
    onCompleted: (ToDoItem) -> Unit,
    user: User,
    onUserUpdate: (User) -> Unit
) {
    val visible = remember(item.id) { mutableStateOf(true) }
    val localCompleted = remember { mutableStateOf(false) }
    val startX = remember { mutableStateOf<Float?>(null) }
    val currentX = remember { mutableStateOf(0f) }
    val strike = remember { mutableStateOf<Offset?>(null) }

    LaunchedEffect(localCompleted.value) {
        if (localCompleted.value && visible.value) {
            kotlinx.coroutines.delay(2000)
            visible.value = false
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

            val strikeBitmap = imageResource(Res.drawable.strikethrough)

            Box(modifier = Modifier.fillMaxWidth().zIndex(1f)) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val left = strike.value?.x ?: startX.value
                    val right = strike.value?.y ?: currentX.value

                    if (left == null) return@Canvas

                    clipRect(left = left, top = 0f, right = right, bottom = size.height) {
                        drawImage(
                            image = strikeBitmap,
                            dstSize = IntSize(size.width.toInt(), size.height.toInt())
                        )
                    }
                }

                Column {
                    val bannerOffset = if (item.priority == Priority.HIGH) 50.dp else 0.dp

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                            .pointerInput(item.id) {
                                detectHorizontalDragGestures(
                                    onDragStart = { offset ->
                                        startX.value = offset.x
                                        currentX.value = offset.x
                                    },
                                    onHorizontalDrag = { _, delta ->
                                        currentX.value += delta
                                    },
                                    onDragEnd = {
                                        val left = minOf(startX.value!!, currentX.value)
                                        val right = maxOf(startX.value!!, currentX.value)
                                        val coverage = (right - left) / size.width

                                        if (coverage >= 0.8f) {
                                            strike.value = Offset(left, right)
                                            onCompleted(item)
                                            updateUser(user, item, onUserUpdate) // Deal with XP and levelling of user
                                            localCompleted.value = true
                                        }

                                        startX.value = null
                                    }
                                )
                            }

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
                    }
                }
            }
        }
    }
}

fun updateUser(
    user: User,
    item: ToDoItem,
    onUserUpdate: (User) -> Unit
) {
    val updatedLevel = user.mainLevel.addXP(item.xpOnCompletion)
    val updatedSkills = user.skills.toMutableMap().apply {
        this[item.skill]?.let { currentSkill ->
            this[item.skill] = currentSkill.copy(
                level = currentSkill.level.addXP(item.xpOnCompletion)
            )
        }
    }

    onUserUpdate(
        user.copy(
            mainLevel = updatedLevel,
            skills = updatedSkills
        )
    )
}

