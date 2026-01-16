package com.exalted.exaltedapp.function

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.progression.SkillType
import com.exalted.exaltedapp.data.progression.User
import com.exalted.exaltedapp.ui.StyledCard
import exaltedapp.composeapp.generated.resources.Res
import exaltedapp.composeapp.generated.resources.character
import org.jetbrains.compose.resources.painterResource
import kotlin.enums.enumEntries

@Composable
fun Character(modifier: Modifier, user : User) {
    Column(modifier = modifier.fillMaxSize()) {
        StyledCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier.padding(20.dp).size(200.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(Res.drawable.character),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                        alignment = Alignment.Center,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth().padding(all = 20.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = user.username,
                        fontSize = 80.sp,
                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                    )
                    Text(
                        text = "Character Level: ${user.mainLevel.level}",
                        fontSize = 30.sp,
                    )
                    Text(
                        text = "XP Remaining: ${(user.mainLevel.xpToNext - user.mainLevel.xp)}",
                        fontSize = 30.sp,
                    )
                    LinearProgressIndicator(
                        progress = { user.mainLevel.xp.toFloat() / user.mainLevel.xpToNext.toFloat() },
                        modifier = Modifier.fillMaxWidth().height(20.dp).padding(top = 8.dp),
                        trackColor = MaterialTheme.colorScheme.background,
                    )
                }
            }
        }
        val listState = rememberLazyListState()

        val skillList = remember(user.skills) {
            user.skills.entries.toList()
        }

        StyledCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState
            ) {
                items(skillList) { (skillType, skill) ->

                    Column(
                        modifier = Modifier.padding(all = 20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "${skillType.displayName}: ${skill.level.level}",
                            fontSize = 40.sp,
                            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                        )
                        Text(
                            text = "XP Remaining: ${(skill.level.xpToNext - skill.level.xp)}",
                            fontSize = 20.sp,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        )
                        val progress = skill.level.xp.toFloat() / (skill.level.xpToNext.toFloat().coerceAtLeast(1f))
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxWidth().height(20.dp).padding(top = 8.dp),
                            trackColor = MaterialTheme.colorScheme.background,
                        )
                    }
                    HorizontalDivider(
                        color = Color.Black.copy(alpha = 0.5f),
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}