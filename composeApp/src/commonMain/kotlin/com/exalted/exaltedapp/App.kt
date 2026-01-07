package com.exalted.exaltedapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.function.Login
import com.exalted.exaltedapp.function.TodoList
import com.exalted.exaltedapp.ui.Dock
import com.exalted.exaltedapp.ui.LightColors
import ui.theme.exaltedTypography

@Composable
fun App() {
    MaterialTheme(
        colorScheme = LightColors,
        typography = exaltedTypography()
    ) {
        var toDoList by rememberSaveable { mutableStateOf(listOf<ToDoItem>()) }
        val completedTasks = toDoList.filter { it.completed }
        var selectedIndex by rememberSaveable { mutableStateOf(0) }

        Scaffold(
            bottomBar = {
                Dock(
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it }
                )
            }
        ) { padding ->
            when (selectedIndex) {
                0 -> TodoList(modifier = Modifier.padding(padding), toDoList = toDoList, onUpdate = { toDoList = it } )
                1 -> Login(modifier = Modifier.padding(padding))
            }
        }
    }
}

