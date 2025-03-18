package com.example.taiyebmustufa34377190.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.taiyebmustufa34377190.navigation.Screens
import com.example.taiyebmustufa34377190.utils.validateCredentials


@Composable
fun LoginScreen(navController: NavHostController) {
    var selectedId by remember { mutableStateOf("012345") }
    var phoneNumber by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Log in", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // ID Input
        OutlinedTextField(
            value = selectedId,
            onValueChange = { selectedId = it },
            label = { Text("My ID (Provided by your Clinician)") },
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone Number Input
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Continue Button
        Button(
            onClick = {
                if (validateCredentials(selectedId, phoneNumber)) {
                    showError = false
                    // Navigate or do next steps here
                    navController.navigate(Screens.FoodIntake.route)
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "Continue", color = Color.White, fontSize = 16.sp)
        }

        if (showError) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Invalid ID or phone number", color = Color.Red, fontSize = 14.sp)
        }
    }
}
