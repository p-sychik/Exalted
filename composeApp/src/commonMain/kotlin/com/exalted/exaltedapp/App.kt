package com.exalted.exaltedapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.exalted.exaltedapp.function.TodoList
import com.exalted.exaltedapp.ui.LightColors
import ui.theme.exaltedTypography

@Composable
fun App() {
    MaterialTheme (
        colorScheme = LightColors,
        typography = exaltedTypography()
    ) {
        TodoList()
    }
}

