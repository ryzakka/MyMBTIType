// File: app/src/main/java/com/dhirekhaf/mytype/SettingsScreen.kt

package com.dhirekhaf.mytype

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dhirekhaf.mytype.data.UserDataRepository
import com.dhirekhaf.mytype.ui.theme.MyTypeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onNavigateToEdit: () -> Unit
) {
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = Color(0xFFF2F3F7)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Grup Akun
            SettingsGroup(title = "ACCOUNT") {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profile",
                    subtitle = "Change your name, profile picture, etc.",
                    onClick = onNavigateToEdit
                )
                SettingsItem(
                    icon = Icons.Default.Refresh,
                    title = "Reset Personality Test",
                    subtitle = "Take the test again to find your type.",
                    onClick = {
                        meViewModel.resetMbtiTest()
                        Toast.makeText(context, "Personality test has been reset.", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Kembali ke laman profil setelah reset
                    }
                )
            }

            // Grup Aplikasi
            SettingsGroup(title = "APPLICATION") {
                SettingsItem(
                    icon = Icons.Default.Delete,
                    title = "Clear Cache",
                    subtitle = "Clear temporary application data.",
                    onClick = {
                        Toast.makeText(context, "Cache cleared (simulation).", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // Tombol Logout
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    Toast.makeText(context, "Logout clicked (simulation).", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text("Logout", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        )
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(content = content)
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xff3f414e))
            Text(text = subtitle, fontSize = 13.sp, color = Color.Gray)
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}
