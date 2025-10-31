// File: app/src/main/java/com/dhirekhaf/mytype/SettingsScreen.kt

package com.dhirekhaf.mytype

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dhirekhaf.mytype.data.UserDataRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onNavigateToEdit: () -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by meViewModel.userData.collectAsState()

    val personalityTheme = getThemeForMbti(userData.mbtiType)
    Box(modifier = Modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition("ken_burns_settings")
        val scale by infiniteTransition.animateFloat(
            1.0f,
            1.1f,
            infiniteRepeatable(tween(25000, easing = LinearEasing), RepeatMode.Reverse),
            "scale_settings"
        )

        Image(
            painter = painterResource(personalityTheme.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().scale(scale)
        )
        FloatingParticles(particleColor = personalityTheme.particleColor)
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Black.copy(0.4f), Color.Black.copy(0.8f))))
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Settings", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsGroup(title = "ACCOUNT") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Edit Profile",
                        subtitle = "Ubah nama, foto profil, dll.",
                        onClick = onNavigateToEdit
                    )
                    SettingsItem(
                        icon = Icons.Default.Refresh,
                        title = "Reset Personality Test",
                        subtitle = "Ambil kembali tes untuk menemukan tipemu.",
                        onClick = {
                            meViewModel.resetMbtiTest()
                            Toast.makeText(context, "Hasil tes kepribadian telah direset.", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    )
                }

                SettingsGroup(title = "APPLICATION") {
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "Tentang Aplikasi",
                        subtitle = "Lihat versi, pengembang, dan info lainnya.",
                        onClick = onNavigateToAbout
                    )
                    SettingsItem(
                        icon = Icons.Default.Delete,
                        title = "Clear Cache",
                        subtitle = "Hapus data sementara aplikasi.",
                        onClick = {
                            Toast.makeText(context, "Cache dibersihkan (simulasi).", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
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
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(text = subtitle, fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f))
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.7f)
        )
    }
}
