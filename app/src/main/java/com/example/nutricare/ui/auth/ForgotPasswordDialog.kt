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
import com.example.nutricare.DarkGreen

@Composable
fun ForgotPasswordDialog(
    viewModel: AuthViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Reset Password", fontWeight = FontWeight.Bold, color = DarkGreen)
        },
        text = {
            Column {
                Text("Enter your registered email address and we'll send you a link to reset your password.", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))

                if (viewModel.forgotError != null) {
                    Text(text = viewModel.forgotError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (viewModel.forgotMessage != null) {
                    Text(text = viewModel.forgotMessage!!, color = Color(0xFF2E7D32), fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                OutlinedTextField(
                    value = viewModel.forgotEmail,
                    onValueChange = { viewModel.forgotEmail = it },
                    placeholder = { Text("Email address") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.sendPasswordReset {} },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                enabled = !viewModel.forgotLoading
            ) {
                if (viewModel.forgotLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text("Send Reset Link")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = DarkGreen)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
