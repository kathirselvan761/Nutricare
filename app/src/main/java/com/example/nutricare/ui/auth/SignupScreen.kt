package com.example.nutricare.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutricare.BadgeBg
import com.example.nutricare.DarkGreen
import com.example.nutricare.AppLogo

@Composable
fun SignupScreen(
    viewModel: AuthViewModel,
    onSignupSuccess: () -> Unit,
    onNavigateBackToLogin: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AppLogo()

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            color = BadgeBg,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Join NutriCare AI Today",
                color = DarkGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Create your account",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkGreen,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Start your personalized nutrition and health journey.",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (viewModel.signupError != null) {
            Text(
                text = viewModel.signupError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Full Name
        Text(text = "Full Name", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkGreen)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = viewModel.signupFullName,
            onValueChange = { viewModel.signupFullName = it },
            placeholder = { Text("Enter your full name", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        Text(text = "Email Address", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkGreen)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = viewModel.signupEmail,
            onValueChange = { viewModel.signupEmail = it },
            placeholder = { Text("Enter email address", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password
        Text(text = "Password (min 8 chars)", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkGreen)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = viewModel.signupPassword,
            onValueChange = { viewModel.signupPassword = it },
            placeholder = { Text("Enter password", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password
        Text(text = "Confirm Password", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkGreen)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = viewModel.signupConfirmPassword,
            onValueChange = { viewModel.signupConfirmPassword = it },
            placeholder = { Text("Confirm your password", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.signup(onSignupSuccess) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            shape = RoundedCornerShape(28.dp),
            enabled = !viewModel.signupLoading
        ) {
            if (viewModel.signupLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Sign Up", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onNavigateBackToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkGreen),
            border = BorderStroke(1.dp, DarkGreen),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Back to Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
