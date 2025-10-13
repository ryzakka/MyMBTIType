// File: app/src/main/java/com/dhirekhaf/mytype/WelcomeScreen.kt
// [VERSI FINAL - DENGAN LATAR GRADASI 4 WARNA BERGERAK]

package com.dhirekhaf.mytype

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onGetInTouchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // --- [MODIFIKASI UTAMA 1: PERSIAPAN ANIMASI WARNA] ---
    val infiniteTransition = rememberInfiniteTransition(label = "welcome_bg_transition")

    // Buat animasi untuk setiap warna, agar transisinya lebih halus
    val color1 by infiniteTransition.animateColor(
        initialValue = analystTheme.primaryColor,
        targetValue = diplomatTheme.primaryColor,
        animationSpec = infiniteRepeatable(tween(8000), RepeatMode.Reverse), label = "c1"
    )
    val color2 by infiniteTransition.animateColor(
        initialValue = diplomatTheme.primaryColor,
        targetValue = sentinelTheme.primaryColor,
        animationSpec = infiniteRepeatable(tween(9000), RepeatMode.Reverse), label = "c2"
    )
    val color3 by infiniteTransition.animateColor(
        initialValue = sentinelTheme.primaryColor,
        targetValue = explorerTheme.primaryColor,
        animationSpec = infiniteRepeatable(tween(10000), RepeatMode.Reverse), label = "c3"
    )
    val color4 by infiniteTransition.animateColor(
        initialValue = explorerTheme.primaryColor,
        targetValue = analystTheme.primaryColor,
        animationSpec = infiniteRepeatable(tween(11000), RepeatMode.Reverse), label = "c4"
    )

    val animatedBrush = Brush.linearGradient(
        colors = listOf(color1, color2, color3, color4),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    // State untuk animasi fade-in konten
    var visibleContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(500)
        visibleContent = true
    }

    // --- [MODIFIKASI UTAMA 2: STRUKTUR TAMPILAN] ---
    Box(
        modifier = modifier
            .fillMaxSize()
            // Terapkan Brush yang sudah dianimasikan sebagai latar belakang
            .background(animatedBrush)
    ) {
        // Lapisan gradien hitam untuk kontras
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.1f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        // Gunakan AnimatedVisibility untuk semua konten agar muncul dengan anggun
        AnimatedVisibility(
            visible = visibleContent,
            enter = fadeIn(animationSpec = tween(1500)),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                Image(
                    painter = painterResource(id = R.drawable.logomytypeteks),
                    contentDescription = "Logo MyType",
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 45.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Hi There!",
                    style = TextStyle(
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ready to discover what makes you, you?",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 28.sp
                    )
                )

                // Spacer untuk mendorong tombol ke bawah
                Spacer(modifier = Modifier.weight(1f))

                // Tombol Aksi Utama
                Column(
                    modifier = Modifier.padding(bottom = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xff2b8a63),
                        shadowElevation = 12.dp,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onGetInTouchClick)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sidikjari),
                            contentDescription = "Find Yourself",
                            modifier = Modifier.padding(18.dp)
                        )
                    }
                    Text(
                        text = "Find Yourself",
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
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(onGetInTouchClick = {})
    }
}
