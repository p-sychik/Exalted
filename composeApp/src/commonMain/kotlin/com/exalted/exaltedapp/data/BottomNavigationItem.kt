package com.exalted.exaltedapp.data

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem (
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val hasTasks: Boolean,
    val badgeCount: Int? = null
)
