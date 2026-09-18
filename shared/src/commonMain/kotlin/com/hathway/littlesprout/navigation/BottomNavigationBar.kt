package com.hathway.littlesprout.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NavItem(val title: String, val icon: String) {
    Home("Home", "🏠"),
    Progress("Progress", "📊"),
    Parents("For Parents", "👨‍👩‍👧")
}

@Composable
fun BottomNavigationBar(
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit
) {
    NavigationBar(
        containerColor = Color.White.copy(alpha = 0.95f),
        tonalElevation = 8.dp
    ) {
        NavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = { Text(item.icon, fontSize = 20.sp) },
                label = { Text(item.title, fontSize = 11.sp) },
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color(0xFF2E7D32),
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFE8F5E9)
                )
            )
        }
    }
}
