package com.exalted.exaltedapp.function

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Login(
    modifier: Modifier = Modifier
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
                text = "Sign Up or Login",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    TODO()
                    // signUp(username, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                Text("Log In", fontSize = 24.sp)
            }
        }
    }
}