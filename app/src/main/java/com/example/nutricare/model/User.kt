package com.example.nutricare.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "patient",
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "email" to email,
            "role" to role,
            "createdAt" to createdAt
        )
    }
}
