package com.dhirekhaf.mytype

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {

    // Definisikan warna-warna utama dari setiap tema untuk gradien
    val colorStops = arrayOf(
        0.0f to analystTheme.primaryColor.copy(alpha = 0.8f),
        0.33f to diplomatTheme.primaryColor.copy(alpha = 0.8f),
        0.66f to sentinelTheme.primaryColor.copy(alpha = 0.8f),
        1.0f to explorerTheme.primaryColor.copy(alpha = 0.8f)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tentang Aplikasi", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent // Buat scaffold transparan agar Box di bawahnya terlihat
    ) { paddingValues ->
        // Box utama sebagai latar belakang
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = colorStops))
        ) {
            // Lapisan gradien hitam untuk meningkatkan kontras teks
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Black.copy(0.2f), Color.Black.copy(0.6f))
                        )
                    )
            )

            // Konten utama
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(Modifier.height(32.dp))

                // 1. Logo Aplikasi
                Image(
                    painter = painterResource(id = R.drawable.logomytypeteks),
                    contentDescription = "Logo MyType",
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 70.dp)
                )

                Spacer(Modifier.height(24.dp))

                // 2. Deskripsi Aplikasi
                Text(
                    text = "MyType adalah aplikasi yang dirancang untuk membantu Anda menemukan dan memahami tipe kepribadian Anda melalui serangkaian tes yang terinspirasi dari teori MBTI. Temukan potensi diri, kekuatan, dan dinamika hubungan Anda dengan orang lain.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White.copy(alpha = 0.9f) // Teks putih
                )
                Spacer(Modifier.height(32.dp))
                Text(
                    "Bagikan Aplikasi",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.qrimage),
                    contentDescription = "Kode QR Unduh Aplikasi",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(8.dp)
                )

                Spacer(Modifier.weight(1f)) // Spacer untuk mendorong info ke bawah

                // 3. Info Versi
                InfoItem(label = "Versi Aplikasi", value = getAppVersion())
                Spacer(Modifier.height(16.dp))

                // 4. Info Pengembang
                InfoItem(label = "Dikembangkan oleh", value = "dhirekhaf")

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}


@Composable
private fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

// Fungsi helper untuk mendapatkan versi aplikasi dari Gradle
@Composable
private fun getAppVersion(): String {
    val context = LocalContext.current
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "1.0.0"
    } catch (e: Exception) {
        "1.0.0"
    }
}
