package com.hathway.littlesprout.presentation.parents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.AppSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: AppSettings,
    onSoundChanged: (Boolean) -> Unit,
    onMusicChanged: (Boolean) -> Unit,
    onAutoPlayChanged: (Boolean) -> Unit,
    onQuietModeChanged: (Boolean) -> Unit,
    onResetProgress: () -> Unit,
    onPrivacySafetyClick: () -> Unit,
    onAboutClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var showResetDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            item { SettingSwitch("Sound", settings.soundEnabled, onSoundChanged) }
            item { SettingSwitch("Music", settings.musicEnabled, onMusicChanged) }
            item { SettingSwitch("Auto-play", settings.autoPlayEnabled, onAutoPlayChanged) }
            item { SettingSwitch("Quiet Mode", settings.quietModeEnabled, onQuietModeChanged) }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            item {
                Button(
                    onClick = { showResetDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Reset Progress")
                }
            }
            
            item {
                TextButton(onClick = onPrivacySafetyClick, modifier = Modifier.fillMaxWidth()) {
                    Text("Privacy & Safety")
                }
            }
            
            item {
                TextButton(onClick = onAboutClick, modifier = Modifier.fillMaxWidth()) {
                    Text("About Little Sprout")
                }
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset learning progress?") },
            text = { Text("This will remove the learning activity history stored on this device.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onResetProgress()
                        showResetDialog = false
                    }
                ) {
                    Text("Reset", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SettingSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
