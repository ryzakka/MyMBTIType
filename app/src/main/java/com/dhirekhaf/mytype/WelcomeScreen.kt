// File: app/src/main/java/com/dhirekhaf/mytype/WelcomeScreen.kt
package com.dhirekhaf.mytype

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

private val WelcomeGayaJudul = TextStyle(
    fontSize = 26.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    color = Color(0xff3f414e)
)
private val WelcomeGayaTombol = TextStyle(fontSize = 25.sp, color = Color.White)

@Composable
fun WelcomeScreen(
    onGetInTouchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- GAMBAR BAGIAN ATAS ---
        Image(
            painter = painterResource(id = R.drawable.welcomeimagetop),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f) // Mengisi 45% bagian atas layar
                .align(Alignment.TopCenter)
        )

        // --- KONTEN DI TENGAH ---
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo16personalities),
                contentDescription = "Logo 16 Personalities",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Hi There!\nReady to discover what makes you, you?",
                style = WelcomeGayaJudul
            )
        }

        // --- GAMBAR DAN TOMBOL BAGIAN BAWAH ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f) // Mengisi 55% bagian bawah layar
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcomeimage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradien gelap di bagian paling bawah agar teks terbaca
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    )
                    .align(Alignment.BottomCenter)
            )

            // Tombol "Get in Touch"
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xff2b8a63),
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onGetInTouchClick)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sidikjari),
                        contentDescription = "Get in Touch",
                        modifier = Modifier
                            .padding(12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Get in Touch!",
                    style = WelcomeGayaTombol
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun WelcomeScreenPreview() {
    WelcomeScreen(onGetInTouchClick = {})
}
