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

