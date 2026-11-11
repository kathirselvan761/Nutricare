package com.example.nutricare.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutricare.data.AuthRepository
import com.example.nutricare.data.UserRepository
import com.example.nutricare.model.User
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    // Login State
    var loginEmail by mutableStateOf("")
    var loginPassword by mutableStateOf("")
    var loginLoading by mutableStateOf(false)
    var loginError by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)

    // Signup State
    var signupFullName by mutableStateOf("")
    var signupEmail by mutableStateOf("")
    var signupPassword by mutableStateOf("")
    var signupConfirmPassword by mutableStateOf("")
    var signupLoading by mutableStateOf(false)
    var signupError by mutableStateOf<String?>(null)
    var signupSuccess by mutableStateOf(false)

    // Forgot Password State
    var forgotEmail by mutableStateOf("")
    var forgotLoading by mutableStateOf(false)
    var forgotMessage by mutableStateOf<String?>(null)
    var forgotError by mutableStateOf<String?>(null)

    // Profile Setup State
    var healthCondition by mutableStateOf("")
    var dietaryPreference by mutableStateOf("")
    var profileSetupLoading by mutableStateOf(false)
    var profileSetupSuccess by mutableStateOf(false)

    val currentUser = authRepository.currentUser

    fun login(onSuccess: () -> Unit) {
        if (loginEmail.isBlank() || loginPassword.isBlank()) {
            loginError = "Please fill in all fields"
            return
        }
        loginLoading = true
        loginError = null

        viewModelScope.launch {
            val result = authRepository.login(loginEmail.trim(), loginPassword)
            loginLoading = false
            if (result.isSuccess) {
                loginSuccess = true
                onSuccess()
            } else {
                loginError = result.exceptionOrNull()?.localizedMessage ?: "Login failed"
            }
        }
    }

    fun signup(onSuccess: () -> Unit) {
        if (signupFullName.isBlank() || signupEmail.isBlank() || signupPassword.isBlank() || signupConfirmPassword.isBlank()) {
            signupError = "Please fill in all fields"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(signupEmail.trim()).matches()) {
            signupError = "Please enter a valid email address"
            return
        }
        if (signupPassword.length < 8) {
            signupError = "Password must be at least 8 characters"
            return
        }
        if (signupPassword != signupConfirmPassword) {
            signupError = "Passwords do not match"
            return
        }

        signupLoading = true
        signupError = null

        viewModelScope.launch {
            val authResult = authRepository.signup(signupEmail.trim(), signupPassword)
            if (authResult.isSuccess) {
                val firebaseUser = authResult.getOrNull()
                if (firebaseUser != null) {
                    val user = User(
                        uid = firebaseUser.uid,
                        name = signupFullName.trim(),
                        email = signupEmail.trim(),
                        role = "patient"
                    )
                    val firestoreResult = userRepository.saveUser(user)
                    signupLoading = false
                    if (firestoreResult.isSuccess) {
                        signupSuccess = true
                        onSuccess()
                    } else {
                        signupError = firestoreResult.exceptionOrNull()?.localizedMessage ?: "Failed to save user profile"
                    }
                } else {
                    signupLoading = false
                    signupError = "User creation failed"
                }
            } else {
                signupLoading = false
                signupError = authResult.exceptionOrNull()?.localizedMessage ?: "Registration failed"
            }
        }
    }

    fun sendPasswordReset(onComplete: () -> Unit) {
        if (forgotEmail.isBlank()) {
            forgotError = "Please enter your email address"
            return
        }
        forgotLoading = true
        forgotError = null
        forgotMessage = null

        viewModelScope.launch {
            val result = authRepository.sendPasswordReset(forgotEmail.trim())
            forgotLoading = false
            if (result.isSuccess) {
                forgotMessage = "Password reset email sent. Check your inbox."
                onComplete()
            } else {
                forgotError = result.exceptionOrNull()?.localizedMessage ?: "Failed to send reset email"
            }
        }
    }

    fun savePatientProfile(onSuccess: () -> Unit) {
        profileSetupLoading = true
        viewModelScope.launch {
            profileSetupLoading = false
            profileSetupSuccess = true
            onSuccess()
        }
    }

    fun logout(onComplete: () -> Unit) {
        authRepository.logout()
        onComplete()
    }
}
