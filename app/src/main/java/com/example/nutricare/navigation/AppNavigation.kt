package com.example.nutricare.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutricare.OnboardingScreen
import com.example.nutricare.ui.auth.*
import com.example.nutricare.ui.home.HomeScreen

@Composable
fun AppNavigation() {
    val authViewModel: AuthViewModel = viewModel()
    var currentScreen by remember {
        mutableStateOf(if (authViewModel.currentUser != null) "HOME" else "ONBOARDING")
    }
    var showForgotPassword by remember { mutableStateOf(false) }

    when (currentScreen) {
        "ONBOARDING" -> {
            OnboardingScreen(onFinish = { currentScreen = "LOGIN" })
        }
        "LOGIN" -> {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = { currentScreen = "HOME" },
                onNavigateToSignup = { currentScreen = "SIGNUP" },
                onForgotPasswordClick = { showForgotPassword = true }
            )
            if (showForgotPassword) {
                ForgotPasswordDialog(
                    viewModel = authViewModel,
                    onDismiss = { showForgotPassword = false }
                )
            }
        }
        "SIGNUP" -> {
            SignupScreen(
                viewModel = authViewModel,
                onSignupSuccess = { currentScreen = "PROFILE_SETUP" },
                onNavigateBackToLogin = { currentScreen = "LOGIN" }
            )
        }
        "PROFILE_SETUP" -> {
            PatientProfileSetupScreen(
                viewModel = authViewModel,
                onProfileSetupComplete = { currentScreen = "HOME" }
            )
        }
        "HOME" -> {
            HomeScreen(
                viewModel = authViewModel,
                onLogout = { currentScreen = "LOGIN" }
            )
        }
    }
}
