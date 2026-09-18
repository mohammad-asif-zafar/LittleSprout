package com.hathway.littlesprout.presentation.parents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySafetyScreen(
    onBackClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy & Safety") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←", fontSize = 24.sp)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Little Sprout is designed for young children ages 2–3.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            item {
                SafetyClaim("Works offline", "✅")
                SafetyClaim("No child account required", "✅")
                SafetyClaim("No advertisements", "✅")
                SafetyClaim("No microphone access", "✅")
                SafetyClaim("No camera access", "✅")
                SafetyClaim("No location access", "✅")
                SafetyClaim("No contacts access", "✅")
                SafetyClaim("No internet required for learning activities", "✅")
                SafetyClaim("Learning progress is stored locally on this device", "✅")
            }
            
            item {
                Button(
                    onClick = onPrivacyPolicyClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Read Privacy Policy")
                }
            }
        }
    }
}

@Composable
private fun SafetyClaim(text: String, icon: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(icon)
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}
