package com.example.taiyebmustufa34377190.utils

import android.content.Context

data class User(val userId: String, val phoneNumber: String, val sex: String)

fun parseCsv(context: Context): List<User> {
    val users = mutableListOf<User>()
    val inputStream = context.assets.open("food_intake_data.csv")
    inputStream.bufferedReader().useLines { lines ->
        lines.drop(1).forEach { line ->
            val tokens = line.split(",")
            if (tokens.size >= 3) {
                users.add(
                    User(
                        userId = tokens[1],
                        phoneNumber = tokens[0],
                        sex = tokens[2]
                    )
                )
            }
        }
    }
    return users
}
fun getUserFoodScore(context: Context, phoneNumber: String, userId: String): String? {
    val inputStream = context.assets.open("food_intake_data.csv")
    inputStream.bufferedReader().useLines { linesSequence ->
        val lines = linesSequence.toList() // convert to list to reuse
        val headers = lines.first().split(",")
        val scoreColumnMale = headers.indexOf("HEIFAtotalscoreMale")
        val scoreColumnFemale = headers.indexOf("HEIFAtotalscoreFemale")
        lines.drop(1).forEach { line ->
            val tokens = line.split(",")
            if (tokens.size >= 4 && tokens[0] == phoneNumber && tokens[1] == userId) {
                return if (tokens[2].equals("Male", ignoreCase = true)) {
                    tokens.getOrNull(scoreColumnMale)
                } else {
                    tokens.getOrNull(scoreColumnFemale)
                }
            }
        }
    }
    return null
}
// Add this to your utils file
fun getUserCategoryScores(context: Context, phoneNumber: String, userId: String): Map<String, Float>? {
    val categoryMap = mutableMapOf<String, Float>()

    try {
        val inputStream = context.assets.open("food_intake_data.csv")
        inputStream.bufferedReader().useLines { linesSequence ->
            val lines = linesSequence.toList()
            val headers = lines.first().split(",")

            // Find the user's row
            val userLine = lines.drop(1).firstOrNull { line ->
                val tokens = line.split(",")
                tokens.size >= 4 && tokens[0] == phoneNumber && tokens[1] == userId
            } ?: return null

            val tokens = userLine.split(",")
            val isMale = tokens[2].equals("Male", ignoreCase = true)

            // Define category mappings with their corresponding column names
            val categoryColumns = mapOf(
                "Vegetables" to if (isMale) "VegetablesHEIFAscoreMale" else "VegetablesHEIFAscoreFemale",
                "Fruits" to if (isMale) "FruitHEIFAscoreMale" else "FruitHEIFAscoreFemale",
                "Grains & Cereals" to if (isMale) "GrainsandcerealsHEIFAscoreMale" else "GrainsandcerealsHEIFAscoreFemale",
                "Whole Grains" to if (isMale) "WholegrainsHEIFAscoreMale" else "WholegrainsHEIFAscoreFemale",
                "Meat & Alternatives" to if (isMale) "MeatandalternativesHEIFAscoreMale" else "MeatandalternativesHEIFAscoreFemale",
                "Dairy" to if (isMale) "DairyandalternativesHEIFAscoreMale" else "DairyandalternativesHEIFAscoreFemale",
                "Water" to if (isMale) "WaterHEIFAscoreMale" else "WaterHEIFAscoreFemale",
                "Unsaturated Fats" to if (isMale) "UnsaturatedFatHEIFAscoreMale" else "UnsaturatedFatHEIFAscoreFemale",
                "Sodium" to if (isMale) "SodiumHEIFAscoreMale" else "SodiumHEIFAscoreFemale",
                "Sugar" to if (isMale) "SugarHEIFAscoreMale" else "SugarHEIFAscoreFemale",
                "Alcohol" to if (isMale) "AlcoholHEIFAscoreMale" else "AlcoholHEIFAscoreFemale",
                "Discretionary Foods" to if (isMale) "DiscretionaryHEIFAscoreMale" else "DiscretionaryHEIFAscoreFemale"
            )

            // Get scores for each category
            categoryColumns.forEach { (category, columnName) ->
                val columnIndex = headers.indexOf(columnName)
                if (columnIndex != -1 && columnIndex < tokens.size) {
                    val score = tokens[columnIndex].toFloatOrNull() ?: 0f
                    categoryMap[category] = score
                }
            }
        }
        return categoryMap
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}