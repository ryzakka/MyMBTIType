// File: app/src/main/java/com/dhirekhaf/mytype/WelcomeScreen.kt
// [KODE FINAL v3.0 - THE GRAND WELCOME, LOGO DIPERBAIKI TOTAL]

package com.dhirekhaf.mytype

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(
    onGetInTouchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                // Gradien premium sebagai latar belakang utama
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD), // Biru langit pucat
                        Color(0xFFF3E5F5), // Ungu lavender lembut
                        Color(0xFFFFF3E0)  // Oranye senja samar
                    )
                )
            )
    ) {
        // --- GAMBAR ILUSTRASI BAWAH (Dengan Efek Fading) ---
        Image(
            painter = painterResource(id = R.drawable.welcomeimage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f) // Mengisi 60% bagian bawah
                .align(Alignment.BottomCenter)
                .alpha(0.8f) // Sedikit transparan agar menyatu
        )

        // Gradien transparan ke gelap untuk memastikan tombol terbaca
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                )
        )

        // --- KONTEN UTAMA (Teks & Logo) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Beri ruang di atas agar logo tidak terlalu ke pinggir
            Spacer(modifier = Modifier.height(100.dp))

            // [PERBAIKAN TOTAL] Logo diperbesar, proporsional, dan jelas
            Image(
                painter = painterResource(id = R.drawable.logomytypeteks),
                contentDescription = "Logo MyType",
                // Gunakan fillMaxWidth dengan fraksi untuk ukuran besar dan responsif
                // Hapus .size() agar tidak distorsi
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            Spacer(modifier = Modifier.height(40.dp)) // Jarak disesuaikan

            Text(
                text = "Hi There!",
                style = TextStyle(
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ready to discover what makes you, you?",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(alpha = 0.6f),
                    lineHeight = 28.sp
                )
            )
        }

        // --- TOMBOL AKSI UTAMA (Floating) ---
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Tombol dibuat lebih besar dan menonjol dengan shadow
            Surface(
                shape = CircleShape,
                color = Color(0xff2b8a63), // Warna hijau khas
                shadowElevation = 12.dp, // Efek shadow agar "mengambang"
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onGetInTouchClick)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sidikjari),
                    contentDescription = "Find Yourself",
                    modifier = Modifier.padding(18.dp) // Sesuaikan padding ikon
                )
            }
            Text(
                text = "Find Yourself", // Teks ajakan yang lebih kuat
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(onGetInTouchClick = {})
    }
}
