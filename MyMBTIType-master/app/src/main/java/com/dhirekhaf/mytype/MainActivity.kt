package com.dhirekhaf.mytype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Welcome(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(40.dp),
        color = Color.White,
        modifier = modifier
            .clip(shape = RoundedCornerShape(40.dp))
            .shadow(elevation = 23.dp,
                shape = RoundedCornerShape(40.dp))
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 414.dp)
                .requiredHeight(height = 896.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcomeimagetop),
                contentDescription = "image 57",
                modifier = Modifier
                    .requiredWidth(width = 414.dp)
                    .requiredHeight(height = 416.dp))
            Image(
                painter = painterResource(id = R.drawable.logo16personalities),
                contentDescription = "image 3",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 174.dp,
                        y = 178.dp)
                    .requiredWidth(width = 62.dp)
                    .requiredHeight(height = 63.dp))
            Text(
                text = "Hi There!\nReady to discover what makes you, you?",
                color = Color(0xff47ad82),
                textAlign = TextAlign.Center,
                // Pastikan AppTypes.type_Gaya_1 sudah didefinisikan
                // Jika belum, ganti dengan TextStyle() untuk sementara
                style = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center), // Contoh pengganti
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 62.dp,
                        y = 256.dp)
                    .requiredWidth(width = 284.dp)
                    .requiredHeight(height = 168.dp))
            Image(
                painter = painterResource(id = R.drawable.welcomeimage),
                contentDescription = "image 4",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = (-134).dp,
                        y = 448.dp)
                    .requiredWidth(width = 683.dp)
                    .requiredHeight(height = 384.dp))
            Image(
                painter = painterResource(id = R.drawable.subtract),
                contentDescription = "Subtract",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 0.dp,
                        y = 743.73.dp)
                    .requiredWidth(width = 414.dp)
                    .requiredHeight(height = 218.dp)
                    .shadow(elevation = 114.dp))
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xff2b8a63),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 170.dp,
                        y = 781.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
            ) {
                Box(
                    modifier = Modifier
                        .requiredWidth(width = 69.dp)
                        .requiredHeight(height = 72.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sidikjari),
                        contentDescription = "fingerprint 1",
                        modifier = Modifier
                            .align(alignment = Alignment.TopStart)
                            .offset(x = 7.dp,
                                y = 8.dp)
                            .requiredWidth(width = 55.dp)
                            .requiredHeight(height = 57.dp))
                }
            }
            Text(
                text = "Get in Touch!",
                color = Color.White,
                style = TextStyle(
                    fontSize = 25.sp),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 155.dp,
                        y = 857.dp)
                    .requiredWidth(width = 125.dp))
        }
    }
}

