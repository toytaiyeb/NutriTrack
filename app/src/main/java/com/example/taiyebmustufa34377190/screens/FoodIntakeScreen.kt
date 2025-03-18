package com.example.taiyebmustufa34377190.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FoodIntakeScreen(navController: NavHostController) {
    val context = LocalContext.current

    var selectedFoods by remember { mutableStateOf(setOf<String>()) }
    var selectedPersona by remember { mutableStateOf("") }
    var biggestMealTime by remember { mutableStateOf("") }
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val personaOptions = listOf(
        "Health Devotee", "Mindful Eater", "Wellness Striver",
        "Balance Seeker", "Health Procrastinator", "Food Carefree"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Food Intake Questionnaire", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Food checkboxes
        val foodOptions = listOf("Fruits", "Vegetables", "Grains", "Red Meat", "Seafood", "Poultry", "Fish", "Eggs", "Nuts/Seeds")
        items(foodOptions) { food ->
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

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Your Persona", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
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

            Spacer(modifier = Modifier.height(16.dp))

            Text("Which persona best fits you?")
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedPersona,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select option") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    personaOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedPersona = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Timings", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = biggestMealTime,
                onValueChange = { biggestMealTime = it },
                label = { Text("When do you eat your biggest meal?") },
                placeholder = { Text("00:00") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = sleepTime,
                onValueChange = { sleepTime = it },
                label = { Text("When do you go to sleep?") },
                placeholder = { Text("00:00") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = wakeTime,
                onValueChange = { wakeTime = it },
                label = { Text("When do you wake up?") },
                placeholder = { Text("00:00") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Save data to SharedPreferences
                    val sharedPref = context.getSharedPreferences("FoodIntakePrefs", Context.MODE_PRIVATE)
                    sharedPref.edit().apply {
                        putStringSet("selectedFoods", selectedFoods)
                        putString("selectedPersona", selectedPersona)
                        putString("biggestMealTime", biggestMealTime)
                        putString("sleepTime", sleepTime)
                        putString("wakeTime", wakeTime)

                        apply()
                    }

                    navController.popBackStack()

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}
