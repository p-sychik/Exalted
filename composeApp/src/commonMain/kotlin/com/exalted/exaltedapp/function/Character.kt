package com.exalted.exaltedapp.function

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.progression.User
import com.exalted.exaltedapp.ui.StyledCard
import exaltedapp.composeapp.generated.resources.Res
import exaltedapp.composeapp.generated.resources.character
import org.jetbrains.compose.resources.painterResource

@Composable
fun Character(modifier: Modifier, user : User) {
    StyledCard {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.character),
                    contentDescription = null,
                    alignment = Alignment.Center,
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(all = 20.dp),
                horizontalAlignment = Alignment.End,

                ) {
                Text(
                    text = user.username,
                    fontSize = 50.sp,
                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                )
                Text(
                    text = "Main Level: ${user.mainLevel.level}",
                    fontSize = 20.sp,
                )
                Text(
                    text = "XP Remaining: ${(user.mainLevel.xpToNext - user.mainLevel.xp)}",
                    fontSize = 20.sp,
                )
                LinearProgressIndicator(
                    progress = { user.mainLevel.xp.toFloat() / user.mainLevel.xpToNext.toFloat() },
                    modifier = Modifier.width(350.dp).height(20.dp).padding(vertical = 5.dp)
                )
            }
        }
    }
}