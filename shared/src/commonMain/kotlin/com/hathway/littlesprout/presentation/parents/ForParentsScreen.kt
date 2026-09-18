package com.hathway.littlesprout.presentation.parents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForParentsScreen(
    onBackClick: () -> Unit,
    onProgressClick: () -> Unit,
    onPrivacySafetyClick: () -> Unit,
    onContentCreditsClick: () -> Unit,
    onContactDeveloperClick: () -> Unit,
    onWebsiteClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAppInformationClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("For Parents") },
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    "Made for little explorers 🌱",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            item { ParentMenuItem("Progress", "📊", onProgressClick) }
            item { ParentMenuItem("Privacy & Safety", "🛡️", onPrivacySafetyClick) }
            item { ParentMenuItem("Content & Credits", "📜", onContentCreditsClick) }
            item { ParentMenuItem("Contact Developer", "📧", onContactDeveloperClick) }
            item { ParentMenuItem("Website", "🌐", onWebsiteClick) }
            item { ParentMenuItem("Settings", "⚙️", onSettingsClick) }
            item { ParentMenuItem("App Information", "ℹ️", onAppInformationClick) }
        }
    }
}

@Composable
private fun ParentMenuItem(
    title: String,
    icon: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Text("›", fontSize = 24.sp, color = MaterialTheme.colorScheme.outline)
        }
    }
}
