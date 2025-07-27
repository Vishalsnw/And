
package com.example.goalguru.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            title = "Welcome to GoalGuru",
            description = "Your AI-powered personal goal coach to help you achieve your dreams.",
            imageRes = android.R.drawable.ic_dialog_info
        ),
        OnboardingPage(
            title = "Set Your Goals",
            description = "Create personalized goals and track your progress with intelligent insights.",
            imageRes = android.R.drawable.ic_menu_agenda
        ),
        OnboardingPage(
            title = "Stay Motivated",
            description = "Get daily reminders and motivational content to keep you on track.",
            imageRes = android.R.drawable.ic_dialog_alert
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(pages[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pagerState.currentPage > 0) {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                ) {
                    Text("Previous")
                }
            } else {
                Spacer(modifier = Modifier.width(72.dp))
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(pages.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.foundation.Canvas(
                            modifier = Modifier.size(6.dp)
                        ) {
                            drawCircle(
                                color = if (index == pagerState.currentPage) 
                                    Color.Blue else Color.Gray
                            )
                        }
                    }
                }
            }

            if (pagerState.currentPage < pages.size - 1) {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Text("Next")
                }
            } else {
                TextButton(
                    onClick = onOnboardingComplete
                ) {
                    Text("Get Started")
                }
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = page.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

private data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)
