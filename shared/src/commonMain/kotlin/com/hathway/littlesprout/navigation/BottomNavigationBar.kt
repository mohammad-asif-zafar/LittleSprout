package com.hathway.littlesprout.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White.copy(alpha = 0.95f),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Text("🏠", fontSize = 20.sp) },
            label = { Text("Home", fontSize = 11.sp) },
            selected = true,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Color(0xFF2E7D32),
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFE8F5E9)
            )
        )
        NavigationBarItem(
            icon = { Text("📊", fontSize = 20.sp) },
            label = { Text("Progress", fontSize = 11.sp) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Text("👨‍👩‍👧", fontSize = 20.sp) },
            label = { Text("For Parents", fontSize = 11.sp) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Text("👤", fontSize = 20.sp) },
            label = { Text("Profile", fontSize = 11.sp) },
            selected = false,
            onClick = {}
        )
    }
}