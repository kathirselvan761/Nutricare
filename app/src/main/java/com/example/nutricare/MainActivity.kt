package com.example.nutricare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutricare.navigation.AppNavigation
import com.example.nutricare.ui.theme.NutricareTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutricareTheme {
                NutriCareApp()
            }
        }
    }
}

val DarkGreen = Color(0xFF1B3B2B)
val LightGreenBg = Color(0xFFF7FBF7)
val BadgeBg = Color(0xFFE8F0EA)

@Composable
fun NutriCareApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightGreenBg
    ) {
        AppNavigation()
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "NutriCare AI",
            fontWeight = FontWeight.Bold,
            color = DarkGreen,
            fontSize = 18.sp
        )
    }
}

data class OnboardingPageData(
    val title: String,
    val boldHeading: String,
    val description: String,
    val footer: String,
    val buttonText: String
)

val onboardingPages = listOf(
    OnboardingPageData(
        title = "Understand Your Health",
        boldHeading = "Your health profile",
        description = "Tell us about your health conditions and treatment details. Build a nutrition profile that sees the whole you.",
        footer = "Your health information stays private and in your control.",
        buttonText = "Next"
    ),
    OnboardingPageData(
        title = "Track Your Food Habits",
        boldHeading = "A balanced day",
        description = "Share your dietary habits, meal patterns and food preferences. Small everyday details make guidance more useful.",
        footer = "No perfect diet needed. Start with what you normally eat.",
        buttonText = "Next"
    ),
    OnboardingPageData(
        title = "Get AI-Powered Nutrition Guidance",
        boldHeading = "Made for you",
        description = "Get personalized food recommendations based on your health profile, treatment information and preferences.",
        footer = "AI supports your choices. It does not replace advice from your care team.",
        buttonText = "Get Started"
    )
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLogo()
            TextButton(onClick = onFinish) {
                Text(
                    text = "Skip",
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
            val page = onboardingPages[pageIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Badge
                Surface(
                    color = BadgeBg,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "PERSONAL CARE, ONE STEP AT A TIME",
                        color = DarkGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = page.title,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkGreen,
                    lineHeight = 44.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = page.boldHeading,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                // Footer text
                Text(
                    text = page.footer,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }

        // Bottom Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pager Indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(onboardingPages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) DarkGreen else Color.LightGray
                    val width = if (pagerState.currentPage == iteration) 24.dp else 8.dp
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(width)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < onboardingPages.size - 1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinish()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = onboardingPages[pagerState.currentPage].buttonText,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
