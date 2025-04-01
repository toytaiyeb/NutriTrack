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

// New function to get specific food category scores (Vegetables, Fruits, etc.)
fun getUserCategoryScores(context: Context, phoneNumber: String, userId: String): Map<String, Double?> {
    val inputStream = context.assets.open("food_intake_data.csv")
    val categoryScores = mutableMapOf<String, Double?>()

    inputStream.bufferedReader().useLines { linesSequence ->
        val lines = linesSequence.toList() // Convert to list to reuse
        val headers = lines.first().split(",")

        // Indexes for the relevant columns
        val vegScoreMale = headers.indexOf("VegetablesHEIFAscoreMale")
        val vegScoreFemale = headers.indexOf("VegetablesHEIFAscoreFemale")
        val fruitScoreMale = headers.indexOf("FruitHEIFAscoreMale")
        val fruitScoreFemale = headers.indexOf("FruitHEIFAscoreFemale")
        val grainsScoreMale = headers.indexOf("GrainsandcerealsHEIFAscoreMale")
        val grainsScoreFemale = headers.indexOf("GrainsandcerealsHEIFAscoreFemale")
        val meatScoreMale = headers.indexOf("MeatandalternativesHEIFAscoreMale")
        val meatScoreFemale = headers.indexOf("MeatandalternativesHEIFAscoreFemale")
        val dairyScoreMale = headers.indexOf("DairyandalternativesHEIFAscoreMale")
        val dairyScoreFemale = headers.indexOf("DairyandalternativesHEIFAscoreFemale")

        lines.drop(1).forEach { line ->
            val tokens = line.split(",")
            if (tokens.size >= 4 && tokens[0] == phoneNumber && tokens[1] == userId) {
                // Get the scores based on sex
                categoryScores["Vegetables"] = if (tokens[2].equals("Male", ignoreCase = true)) {
                    tokens.getOrNull(vegScoreMale)?.toDoubleOrNull()
                } else {
                    tokens.getOrNull(vegScoreFemale)?.toDoubleOrNull()
                }
                categoryScores["Fruits"] = if (tokens[2].equals("Male", ignoreCase = true)) {
                    tokens.getOrNull(fruitScoreMale)?.toDoubleOrNull()
                } else {
                    tokens.getOrNull(fruitScoreFemale)?.toDoubleOrNull()
                }
                categoryScores["Grains"] = if (tokens[2].equals("Male", ignoreCase = true)) {
                    tokens.getOrNull(grainsScoreMale)?.toDoubleOrNull()
                } else {
                    tokens.getOrNull(grainsScoreFemale)?.toDoubleOrNull()
                }
                categoryScores["Meat"] = if (tokens[2].equals("Male", ignoreCase = true)) {
                    tokens.getOrNull(meatScoreMale)?.toDoubleOrNull()
                } else {
                    tokens.getOrNull(meatScoreFemale)?.toDoubleOrNull()
                }
                categoryScores["Dairy"] = if (tokens[2].equals("Male", ignoreCase = true)) {
                    tokens.getOrNull(dairyScoreMale)?.toDoubleOrNull()
                } else {
                    tokens.getOrNull(dairyScoreFemale)?.toDoubleOrNull()
                }
            }
        }
    }

    return categoryScores
}