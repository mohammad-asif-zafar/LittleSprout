package com.hathway.littlesprout.presentation.parents

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.util.AppInfo
import com.hathway.littlesprout.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoScreen(
    appInfo: AppInfo,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Information") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←", fontSize = 24.sp)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Little Sprout", style = MaterialTheme.typography.headlineMedium)
            Text("Early learning for little explorers", style = MaterialTheme.typography.bodyLarge)
            Text("Designed for ages 2–3", style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Version", appInfo.version)
            InfoRow("Developer", Constants.DEVELOPER_NAME)
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                "Made with care for little explorers and their families.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
