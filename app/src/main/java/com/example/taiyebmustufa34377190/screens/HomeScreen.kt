package com.example.taiyebmustufa34377190.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.taiyebmustufa34377190.R
import com.example.taiyebmustufa34377190.navigation.Screens
import com.example.taiyebmustufa34377190.utils.getUserFoodScore


@Composable
fun HomeScreen(navController: NavHostController, phoneNumber: String, userId: String) {
    val context = LocalContext.current
    var score by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        score = getUserFoodScore(context, phoneNumber, userId)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(padding) // Scaffold padding
        ) {
            // --- Header Section ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Hello,", fontSize = 20.sp)
                    Text(
                        text = userId,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "You’ve already filled in your Food Intake Questionnaire, but you can change details here:",
                        fontSize = 12.sp
                    )
                }
                IconButton(
                    onClick = { navController.navigate(Screens.FoodIntake.route) },
                    modifier = Modifier
                        .background(Color(0xFF6200EE), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Image Section ---
            Image(
                painter = painterResource(id = R.drawable.food),
                contentDescription = "Food plate",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Score Card ---
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
                        score?.let {
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

            Spacer(modifier = Modifier.height(24.dp))

            // --- Explanation Section ---
            Text(text = "What is the Food Quality Score?", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.\n\nThis personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
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
            val isSelected = item == "Home"

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
                        "Insights" -> navController.navigate(Screens.Home.createRouteIs(phoneNumber, userId))

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
//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//    NavigationBar(
//        containerColor = Color.White
//    ) {
//        val items = listOf("Home", "Insights", "NutriCoach", "Settings")
//
//        items.forEach { item ->
//            val isSelected = item == "Home"
//
//            NavigationBarItem(
//                icon = {
//                    when (item) {
//                        "Home" -> Icon(Icons.Default.Home, contentDescription = "Home")
//                        "Insights" -> Icon(Icons.Default.ShoppingCart, contentDescription = "Insights")
//                        "NutriCoach" -> Icon(Icons.Default.Person, contentDescription = "NutriCoach")
//                        "Settings" -> Icon(Icons.Default.Settings, contentDescription = "Settings")
//                    }
//                },
//                label = {
//                    Text(
//                        text = item,
//                        fontSize = 10.sp,
//                        color = if (isSelected) Color(0xFF6200EE) else Color.Gray
//                    )
//                },
//                selected = isSelected,
//                onClick = {
//                    // TODO: handle navController.navigate() here
//                },
//                colors = NavigationBarItemDefaults.colors(
//                    indicatorColor = Color.Transparent,
//                    selectedIconColor = Color(0xFF6200EE),
//                    unselectedIconColor = Color.Gray
//                )
//            )
//        }
//    }
//}

