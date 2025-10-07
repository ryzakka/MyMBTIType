package com.dhirekhaf.mytype

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

// 1. Data class untuk merepresentasikan setiap item di NavBar
data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

// 2. Composable untuk Bottom Navigation Bar yang modern
@Composable
fun ModernBottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Daftar item navigasi
    val items = listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "beranda"
        ),
        BottomNavItem(
            title = "Personalities",
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            route = "personalities"
        ),
        BottomNavItem(
            title = "Me",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "me"
        )
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White, // Warna latar NavBar
        contentColor = Color(0xff3f414e) // Warna default untuk konten
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) { // Hanya navigasi jika belum terpilih
                        onNavigate(item.route)
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xff47AD82), // Warna ikon saat terpilih
                    selectedTextColor = Color(0xff47AD82), // Warna teks saat terpilih
                    indicatorColor = Color(0xff47AD82).copy(alpha = 0.1f), // Warna latar belakang indikator
                    unselectedIconColor = Color.Gray, // Warna ikon saat tidak terpilih
                    unselectedTextColor = Color.Gray // Warna teks saat tidak terpilih
                )
            )
        }
    }
}

// 3. Preview untuk melihat hasilnya
@Preview
@Composable
private fun ModernBottomNavBarPreview() {
    // Contoh saat berada di rute "personalities"
    ModernBottomNavBar(currentRoute = "personalities", onNavigate = {})
}
