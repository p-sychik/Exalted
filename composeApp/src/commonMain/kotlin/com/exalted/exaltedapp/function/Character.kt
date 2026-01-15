package com.exalted.exaltedapp.function

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.progression.User

@Composable
fun Character(modifier: Modifier, user : User) {
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
            Text(
                text = "Main Level: ${user.mainLevel.level}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "XP: ${user.mainLevel.xp}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "XP To Next Level: ${user.mainLevel.xpToNext}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            LinearProgressIndicator(
                progress = { user.mainLevel.xp.toFloat() / user.mainLevel.xpToNext.toFloat() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}