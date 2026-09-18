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
fun CreditsScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Content & Credits") },
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
            item { CreditSection("Illustrations", "Some illustrations and visual assets were created specifically for Little Sprout using original artwork and/or AI-assisted tools.") }
            item { CreditSection("Audio", "Some songs, sound effects, and voice/audio assets were created using original content and/or AI-assisted audio tools.") }
            item { CreditSection("Music & Lyrics", "Original lyrics and musical content were created for Little Sprout.") }
            item { CreditSection("Design", "UI design and artwork were created for Little Sprout.") }
            item { CreditSection("Third-Party Assets", "Any third-party assets used in Little Sprout are used according to their applicable licenses and terms.") }
        }
    }
}

@Composable
private fun CreditSection(title: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}
