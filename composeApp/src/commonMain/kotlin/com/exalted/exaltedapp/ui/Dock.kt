package com.exalted.exaltedapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exalted.exaltedapp.data.BottomNavigationItem
import exaltedapp.composeapp.generated.resources.Res
import exaltedapp.composeapp.generated.resources.Tasks
import exaltedapp.composeapp.generated.resources.character
import org.jetbrains.compose.resources.painterResource

@Composable
fun Dock(
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Tasks",
            selectedIcon = painterResource(Res.drawable.Tasks),
            unselectedIcon = painterResource(Res.drawable.Tasks),
            hasTasks = false
        ),
        BottomNavigationItem(
            title = "Account",
            selectedIcon = painterResource(Res.drawable.character),
            unselectedIcon = painterResource(Res.drawable.character),
            hasTasks = false
        )
    )

    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
        tonalElevation = 8.dp
    ) {
        NavigationBar (
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.height(120.dp)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = { onSelect(index) },
                    label = { Text(text = item.title, fontSize = 30.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.background
                    ),
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge { Text(item.badgeCount.toString()) }
                                } else if (item.hasTasks) {
                                    Badge()
                                }
                            }
                        ) {
                            Box(
                               contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    painter = if (index == selectedIndex)
                                        item.selectedIcon
                                    else item.unselectedIcon,
                                    contentDescription = item.title,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(70.dp),
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
