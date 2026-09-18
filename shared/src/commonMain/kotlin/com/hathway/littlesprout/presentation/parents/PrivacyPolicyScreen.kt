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
fun PrivacyPolicyScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
                    "Effective Date: January 1, 2024",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            
            item { PrivacySection("1. Introduction", "Little Sprout is an offline early-development learning app for toddlers. We take privacy seriously and designed this app to be safe for children.") }
            item { PrivacySection("2. Information We Collect", "Little Sprout does not collect any personal information, device identifiers, or usage data that is transmitted to us or any third party.") }
            item { PrivacySection("3. Information We Do Not Collect", "We do not collect: Name, Age, Email, Location, Contacts, Camera data, or Microphone data.") }
            item { PrivacySection("4. Offline Operation", "The app is designed to work entirely offline. No internet connection is required for any learning activities.") }
            item { PrivacySection("5. Local Learning Progress", "Learning progress and app settings are stored locally on your device only. This data is never synchronized to the cloud.") }
            item { PrivacySection("6. Children's Privacy", "The app is intended for children ages 2-3. Since no data is collected, it is inherently safe and compliant with children's privacy standards.") }
            item { PrivacySection("7. Third-Party Services", "We do not use any third-party analytics, tracking, or advertising SDKs.") }
            item { PrivacySection("8. Advertising", "There are no advertisements in Little Sprout.") }
        }
    }
}

@Composable
private fun PrivacySection(title: String, content: String) {
    Column {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(content, style = MaterialTheme.typography.bodyMedium)
    }
}
