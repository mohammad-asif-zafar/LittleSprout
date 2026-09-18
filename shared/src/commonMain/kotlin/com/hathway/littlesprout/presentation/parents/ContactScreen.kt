package com.hathway.littlesprout.presentation.parents

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    onBackClick: () -> Unit,
    onEmailClick: () -> Unit,
    onWebsiteClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Little Sprout") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Have a question or found a problem?", style = MaterialTheme.typography.bodyLarge)
            
            Column {
                Text("Email:", style = MaterialTheme.typography.labelLarge)
                TextButton(onClick = onEmailClick, contentPadding = PaddingValues(0.dp)) {
                    Text(Constants.SUPPORT_EMAIL)
                }
            }
            
            Column {
                Text("Website:", style = MaterialTheme.typography.labelLarge)
                TextButton(onClick = onWebsiteClick, contentPadding = PaddingValues(0.dp)) {
                    Text(Constants.WEBSITE_URL)
                }
            }
        }
    }
}
