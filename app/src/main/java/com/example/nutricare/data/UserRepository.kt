package com.example.nutricare.data

import com.example.nutricare.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firestore: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    suspend fun saveUser(user: User): Result<Unit> {
        return try {
            firestore.collection("users").document(user.uid).set(user.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(uid: String): Result<User?> {
        return try {
            val snapshot = firestore.collection("users").document(uid).get().await()
            val user = snapshot.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
