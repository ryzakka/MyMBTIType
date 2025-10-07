// File: app/src/main/java/com/dhirekhaf/mytype/HiThereScreen.kt
package com.dhirekhaf.mytype

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun HiThereScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Navigasi otomatis setelah beberapa detik
    LaunchedEffect(key1 = true) {
        delay(2000) // Tunda selama 2 detik
        onNavigateToHome()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onNavigateToHome) // Bisa di-klik untuk mempercepat
    ) {
        // Gambar latar belakang yang di-blur
        Image(
            painter = painterResource(id = R.drawable.image46),
            contentDescription = "Blurred Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 50.dp)
        )

        // Konten utama di tengah
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth(0.7f) // Mengisi 70% lebar layar
                    .aspectRatio(1f) // Menjaga rasio 1:1
            )
        }

        // Gambar dekorasi kupu-kupu di bagian bawah
        Image(
            painter = painterResource(id = R.drawable.welcomekupu),
            contentDescription = "Decoration",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f) // Mengisi 90% lebar layar
                .padding(bottom = 50.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun HiThereScreenPreview() {
    HiThereScreen(onNavigateToHome = {})
}
