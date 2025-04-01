package com.example.taiyebmustufa34377190.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.taiyebmustufa34377190.navigation.Screens
import com.example.taiyebmustufa34377190.utils.getUserCategoryScores
import com.example.taiyebmustufa34377190.utils.getUserFoodScore

@Composable
fun InsightsScreen(navController: NavHostController, phoneNumber: String, userId: String) {
    var totalScore by remember { mutableStateOf<String?>(null) }
    var categoryScores by remember { mutableStateOf<Map<String, Float>?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        totalScore = getUserFoodScore(context, phoneNumber, userId)
        categoryScores = getUserCategoryScores(context, phoneNumber, userId)
    }

    // Default max scores for each category
    val categoryMaxScores = mapOf(
        "Vegetables" to 10f,
        "Fruits" to 10f,
        "Grains & Cereals" to 10f,
        "Whole Grains" to 10f,
        "Meat & Alternatives" to 10f,
        "Dairy" to 10f,
        "Water" to 10f,
        "Unsaturated Fats" to 10f,
        "Sodium" to 10f,
        "Sugar" to 10f,
        "Alcohol" to 10f,
        "Discretionary Foods" to 10f
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBars(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(24.dp)
                .padding(padding)
        ) {
            Text(
                text = "Insights: Food Score",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Display category progress bars
            categoryScores?.let { scores ->
                scores.forEach { (category, score) ->
                    val maxScore = categoryMaxScores[category] ?: 10f
                    ProgressBarCategory(
                        category = category,
                        progress = score / maxScore,
                        scoreText = "${score.toInt()}/${maxScore.toInt()}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } ?: run {
                // Show loading state if scores aren't loaded yet
                categoryMaxScores.forEach { (category, maxScore) ->
                    ProgressBarCategory(
                        category = category,
                        progress = 0f,
                        scoreText = "0/${maxScore.toInt()}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Total Food Quality Score",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Score Card
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Your Food Quality score", fontSize = 14.sp)
                        totalScore?.let {
                            Text(
                                text = "$it/100",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                        } ?: Text("Loading...", fontSize = 16.sp)
                    }
                    Text(text = "See all scores >", fontSize = 12.sp, color = Color.Gray)
                }
            }

            LinearProgressIndicator(
                progress = (totalScore?.toFloat() ?: 0f) / 100f,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF6200EE),
                trackColor = Color.LightGray
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
fun ProgressBarCategory(category: String, progress: Float, scoreText: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = category, fontSize = 14.sp)
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF6200EE),
            trackColor = Color.LightGray
        )
        Text(
            text = scoreText,
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
        val context = LocalContext.current

        // Retrieve phoneNumber and userId from shared preferences
        val sharedPrefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val phoneNumber = sharedPrefs.getString("phoneNumber", "") ?: ""
        val userId = sharedPrefs.getString("userId", "") ?: ""

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
                        "Home" -> navController.navigate(Screens.Home.createRouteHs(phoneNumber, userId))
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