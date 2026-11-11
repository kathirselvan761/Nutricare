package com.example.nutricare.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutricare.BadgeBg
import com.example.nutricare.DarkGreen
import com.example.nutricare.AppLogo

@Composable
fun PatientProfileSetupScreen(
    viewModel: AuthViewModel,
    onProfileSetupComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AppLogo()

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            color = BadgeBg,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "PROFILE SETUP",
                color = DarkGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tell us about your health",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkGreen,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This helps us personalize your nutrition recommendations.",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Health Condition / Goals", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkGreen)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.healthCondition,
            onValueChange = { viewModel.healthCondition = it },
            placeholder = { Text("e.g. Diabetes management, Weight loss", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Dietary Preferences", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkGreen)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.dietaryPreference,
            onValueChange = { viewModel.dietaryPreference = it },
            placeholder = { Text("e.g. Vegetarian, Low sodium", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.savePatientProfile(onProfileSetupComplete) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            shape = RoundedCornerShape(28.dp),
            enabled = !viewModel.profileSetupLoading
        ) {
            if (viewModel.profileSetupLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Continue to Dashboard", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
