package com.hathway.littlesprout.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.OnboardingPage
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_onboarding_1
import littlesprout.shared.generated.resources.img_onboarding_2
import littlesprout.shared.generated.resources.img_onboarding_3
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel, onOnboardingFinished: () -> Unit
) {
    val pages by viewModel.pages.collectAsState()

    OnboardingContent(
        pages = pages, onOnboardingFinished = onOnboardingFinished
    )
}

@Composable
fun OnboardingContent(
    pages: List<OnboardingPage>, onOnboardingFinished: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
        ) { position ->
            if (position < pages.size) {
                PagerScreen(page = pages[position])
            }
        }

        // Skip Button - Top End as per design
        TextButton(
            onClick = onOnboardingFinished,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 32.dp, end = 16.dp)
        ) {
            Text("Skip", color = Color.Gray)
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page Indicator
            Row(
                Modifier.height(50.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color(0xFF4CAF50) else Color.LightGray.copy(
                            alpha = 0.5f
                        )
                    Box(
                        modifier = Modifier.padding(4.dp).clip(CircleShape).background(color)
                            .size(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Next/Get Started Button
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onOnboardingFinished()
                    }
                },
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(if (pagerState.currentPage < pages.size - 1) "Next" else "Get Started")
            }
        }
    }
}

@Composable
fun PagerScreen(page: OnboardingPage) {
    Image(
        painter = painterResource(page.image),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun OnboardingScreenPreview() {
    MaterialTheme {
        // Generating clean layout mockup slide pages to emulate multiple onboarding states
        val mockPages = listOf(
            OnboardingPage(
                title = "Welcome",
                description = "Learn alphabets!",
                image = Res.drawable.img_onboarding_1
            ), OnboardingPage(
                title = "Numbers",
                description = "Count fun things!",
                image = Res.drawable.img_onboarding_2
            ), OnboardingPage(
                title = "Colors",
                description = "Explore bright splashes!",
                image = Res.drawable.img_onboarding_3
            )
        )

        OnboardingContent(
            pages = mockPages, onOnboardingFinished = {})
    }
}