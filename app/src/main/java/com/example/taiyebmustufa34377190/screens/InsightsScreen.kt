package com.example.taiyebmustufa34377190.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.taiyebmustufa34377190.navigation.Screens

@Composable
fun InsightsScreen(navController: NavHostController) {
    val categories = mapOf(
        "Vegetables" to 10,
        "Fruits" to 10,
        "Grains & Cereals" to 10,
        "Whole Grains" to 10,
        "Meat & Alternatives" to 10,
        "Dairy" to 10,
        "Water" to 2,
        "Unsaturated Fats" to 10,
        "Sodium" to 10,
        "Sugar" to 10,
        "Alcohol" to 2,
        "Discretionary Foods" to 8
    )

    val totalScore = 40 // This should be dynamically calculated

    Scaffold(
        bottomBar = {
            BottomNavigationBars(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(padding)
        ) {
            Text(
                text = "Insights: Food Score",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            categories.forEach { (category, score) ->
                ProgressBarCategory(category = category, progress = score * 10)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Total Food Quality Score",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LinearProgressIndicator(
                progress = totalScore / 100f,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF6200EE),
                trackColor = Color.LightGray
            )
            Text(
                text = "$totalScore/100",
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Share logic */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Share with someone")
                }
                Button(
                    onClick = { /* Improve diet logic */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Improve my diet!")
                }
            }
        }
    }
}

@Composable
fun ProgressBarCategory(category: String, progress: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = category, fontSize = 14.sp)
        LinearProgressIndicator(
            progress = progress / 100f,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF6200EE),
            trackColor = Color.LightGray
        )
        Text(
            text = "$progress/10",
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun BottomNavigationBars(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White
    ) {
        val items = listOf("Home", "Insights", "NutriCoach", "Settings")

        items.forEach { item ->
            val isSelected = item == "Insights"

            NavigationBarItem(
                icon = {
                    when (item) {
                        "Home" -> Icon(Icons.Default.Home, contentDescription = "Home")
                        "Insights" -> Icon(Icons.Default.ShoppingCart, contentDescription = "Insights")
                        "NutriCoach" -> Icon(Icons.Default.Person, contentDescription = "NutriCoach")
                        "Settings" -> Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                label = {
                    Text(
                        text = item,
                        fontSize = 10.sp,
                        color = if (isSelected) Color(0xFF6200EE) else Color.Gray
                    )
                },
                selected = isSelected,
                onClick = {
                    when (item) {
                        "Home" -> navController.navigate(Screens.Home.route)
                        "Insights" -> navController.navigate(Screens.Insights.route)

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color(0xFF6200EE),
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}