package com.example.taiyebmustufa34377190.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FoodIntakeScreen(navController: NavHostController) {
    var selectedFoods by remember { mutableStateOf(setOf<String>()) }
    var selectedPersona by remember { mutableStateOf("") }
    var selectedPersonaDropdown by remember { mutableStateOf("") }

    var biggestMealTime by remember { mutableStateOf("") }
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Food Intake Questionnaire", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Food checkboxes
        val foodOptions = listOf("Fruits", "Vegetables", "Grains", "Red Meat", "Seafood", "Poultry", "Fish", "Eggs", "Nuts/Seeds")
        foodOptions.forEach { food ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedFoods = if (selectedFoods.contains(food)) {
                            selectedFoods - food
                        } else {
                            selectedFoods + food
                        }
                    }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = selectedFoods.contains(food), onCheckedChange = null)
                Text(food)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Persona Buttons
        Text("Your Persona", style = MaterialTheme.typography.titleMedium)
        val personaOptions = listOf(
            "Health Devotee", "Mindful Eater", "Wellness Striver",
            "Balance Seeker", "Health Procrastinator", "Food Carefree"
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            personaOptions.forEach { persona ->
                Button(
                    onClick = { selectedPersona = persona },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedPersona == persona) Color(0xFF6200EE) else Color.LightGray
                    )
                ) {
                    Text(persona, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Persona Dropdown
        Text("Which persona best fits you?")
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {}
        ) {
            TextField(
                value = selectedPersonaDropdown,
                onValueChange = { selectedPersonaDropdown = it },
                readOnly = true,
                placeholder = { Text("Select option") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Timing Inputs
        Text("Timings", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = biggestMealTime,
            onValueChange = { biggestMealTime = it },
            label = { Text("When do you eat your biggest meal?") },
            placeholder = { Text("00:00") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = sleepTime,
            onValueChange = { sleepTime = it },
            label = { Text("When do you go to sleep?") },
            placeholder = { Text("00:00") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = wakeTime,
            onValueChange = { wakeTime = it },
            label = { Text("When do you wake up?") },
            placeholder = { Text("00:00") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = {
                // You can later save these values to SharedPreferences or pass it to another screen
                navController.popBackStack() // Go back or navigate forward
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text("Save", color = Color.White)
        }
    }
}
