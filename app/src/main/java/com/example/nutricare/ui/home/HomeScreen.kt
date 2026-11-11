package com.example.nutricare.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutricare.BadgeBg
import com.example.nutricare.DarkGreen
import com.example.nutricare.AppLogo
import com.example.nutricare.ui.auth.AuthViewModel

@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    val currentUser = viewModel.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLogo()
            TextButton(
                onClick = {
                    viewModel.logout(onLogout)
                }
            ) {
                Text("Logout", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            color = BadgeBg,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "PATIENT DASHBOARD",
                color = DarkGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome back,\n${currentUser?.email ?: "Patient"}",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkGreen,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Your AI Nutrition Plan", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DarkGreen)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Personalized recommendations based on your health profile are ready.", fontSize = 14.sp, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
