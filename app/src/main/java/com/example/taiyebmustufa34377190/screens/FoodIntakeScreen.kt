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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FoodIntakeScreen(navController: NavHostController) {
    val context = LocalContext.current

    var selectedFoods by remember { mutableStateOf(setOf<String>()) }
    var selectedPersona by remember { mutableStateOf("") }
    var biggestMealTime by remember { mutableStateOf("") }
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    var showPersonaModal by remember { mutableStateOf(false) }
    var activePersona by remember { mutableStateOf("") }

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
                        onClick = {
                            activePersona = persona
                            showPersonaModal = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedPersona == persona) Color(0xFF6200EE) else Color.LightGray
                        )
                    ) {
                        Text(persona, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Selected Persona: ${selectedPersona.ifEmpty { "No persona selected" }}", color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Timing Questions
            OutlinedTextField(
                value = biggestMealTime,
                onValueChange = { biggestMealTime = it },
                label = { Text("When do you eat your biggest meal?") },
                placeholder = { Text("e.g., 13:00") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = sleepTime,
                onValueChange = { sleepTime = it },
                label = { Text("When do you go to sleep?") },
                placeholder = { Text("e.g., 22:30") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = wakeTime,
                onValueChange = { wakeTime = it },
                label = { Text("When do you wake up?") },
                placeholder = { Text("e.g., 07:00") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val sharedPref = context.getSharedPreferences("FoodIntakePrefs", Context.MODE_PRIVATE)
                    sharedPref.edit().apply {
                        putStringSet("selectedFoods", selectedFoods)
                        putString("selectedPersona", selectedPersona)
                        putString("biggestMealTime", biggestMealTime)
                        putString("sleepTime", sleepTime)
                        putString("wakeTime", wakeTime)
                        apply()
                    }
                    Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Save", color = Color.White)
            }
        }
    }

    if (showPersonaModal) {
        AlertDialog(
            onDismissRequest = { showPersonaModal = false },
            title = { Text(activePersona) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(getPersonaDescription(activePersona))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedPersona = activePersona
                    showPersonaModal = false
                }) {
                    Text("Select")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPersonaModal = false }) {
                    Text("Close")
                }
            }
        )
    }
}

fun getPersonaDescription(personaName: String): String {
    return when (personaName) {
        "Health Devotee" -> "You are committed to healthy eating, focusing on nutrient-dense foods and an active lifestyle."
        "Mindful Eater" -> "You pay attention to how and why you eat, often practicing mindful eating."
        "Wellness Striver" -> "You try to make balanced food choices and strive for wellness, though you indulge occasionally."
        "Balance Seeker" -> "You aim for balance between health and enjoyment in your diet."
        "Health Procrastinator" -> "You want to eat healthier but tend to delay changes."
        "Food Carefree" -> "You eat what you love without worrying too much about health trends."
        else -> "No description available."
    }
}
