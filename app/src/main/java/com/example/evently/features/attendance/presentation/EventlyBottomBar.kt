package com.example.evently.features.attendance.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Bottom Nav destination enum ─────────────────────────────────────────────

enum class BottomNavDestination(val label: String) {
    HOME("Beranda"),
    EXPLORE("Jelajahi"),
    TICKET("Sertifikat"),
    PROFILE("Profil")
}

// ─── Reusable BottomBar Component ────────────────────────────────────────────

@Composable
fun EventlyBottomBar(
    selected: BottomNavDestination = BottomNavDestination.TICKET,
    onItemSelected: (BottomNavDestination) -> Unit = {}
) {
    val orange = Color(0xFFF97316)

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        BottomNavDestination.entries.forEach { dest ->
            val isSelected = dest == selected

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(dest) },
                icon = {
                    when (dest) {
                        BottomNavDestination.HOME -> Icon(
                            Icons.Outlined.Home,
                            contentDescription = dest.label,
                            modifier = Modifier.size(24.dp)
                        )
                        BottomNavDestination.EXPLORE -> Icon(
                            Icons.Outlined.Explore,
                            contentDescription = dest.label,
                            modifier = Modifier.size(24.dp)
                        )
                        BottomNavDestination.TICKET -> Icon(
                            Icons.Outlined.AccountCircle, // placeholder; replace with custom ticket icon
                            contentDescription = dest.label,
                            modifier = Modifier.size(24.dp)
                        )
                        BottomNavDestination.PROFILE -> Icon(
                            Icons.Outlined.AccountCircle,
                            contentDescription = dest.label,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = dest.label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = orange,
                    selectedTextColor = orange,
                    unselectedIconColor = Color(0xFF9CA3AF),
                    unselectedTextColor = Color(0xFF9CA3AF),
                    indicatorColor = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventlyBottomBarPreview() {
    EventlyBottomBar()
}
