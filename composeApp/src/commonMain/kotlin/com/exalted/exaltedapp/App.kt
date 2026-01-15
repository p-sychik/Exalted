package com.exalted.exaltedapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.exalted.exaltedapp.data.ToDoItem
import com.exalted.exaltedapp.data.progression.User
import com.exalted.exaltedapp.factory.createGuestUser
import com.exalted.exaltedapp.function.Character
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
        var user by remember { mutableStateOf(createGuestUser()) }

        Scaffold(
            bottomBar = {
                Dock(
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it }
                )
            }
        ) { padding ->
            when (selectedIndex) {
                0 -> TodoList(
                    modifier = Modifier.padding(padding),
                    toDoList = toDoList,
                    onUpdate = { toDoList = it },
                    user = user,
                    onUserUpdate = { user = it } )

                1 -> if (user != null) {
                    Character(
                        modifier = Modifier.padding(padding),
                        user = user) }
                else
                    Login(modifier = Modifier.padding(padding))
            }
        }
    }
}

