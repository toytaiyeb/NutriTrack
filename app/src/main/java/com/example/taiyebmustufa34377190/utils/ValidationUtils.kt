package com.example.taiyebmustufa34377190.utils

fun validateCredentials(id: String, phone: String, users: List<User>): Boolean {
    val matchedUser = users.find { it.userId == id }
    return matchedUser?.phoneNumber == phone
}
