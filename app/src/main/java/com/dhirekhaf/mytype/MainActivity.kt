package com.dhirekhaf.myapp


import com.dhirekhaf.myapp.R
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
import androidx.compose.material3.MaterialTheme
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Welcome() // panggil composable Welcome
            }
        }
    }
}

@Composable
fun Welcome(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(40.dp),
        color = Color.White,
        modifier = modifier
            .clip(shape = RoundedCornerShape(40.dp))
            .shadow(elevation = 23.dp, shape = RoundedCornerShape(40.dp))
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 414.dp)
                .requiredHeight(height = 896.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcomeimagetop),
                contentDescription = "image top",
                modifier = Modifier
                    .requiredWidth(414.dp)
                    .requiredHeight(416.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.logo16personalities),
                contentDescription = "logo",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 174.dp, y = 178.dp)
                    .requiredWidth(62.dp)
                    .requiredHeight(63.dp)
            )

            Text(
                text = "Hi There!\nReady to discover what makes you, you?",
                color = Color(0xff47ad82),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 62.dp, y = 256.dp)
                    .requiredWidth(284.dp)
                    .requiredHeight(168.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.welcomeimage),
                contentDescription = "welcome image",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-134).dp, y = 448.dp)
                    .requiredWidth(683.dp)
                    .requiredHeight(384.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.subtract),
                contentDescription = "subtract",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 0.dp, y = 743.73.dp)
                    .requiredWidth(414.dp)
                    .requiredHeight(218.dp)
                    .shadow(elevation = 114.dp)
            )

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xff2b8a63),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 170.dp, y = 781.dp)
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Box(
                    modifier = Modifier
                        .requiredWidth(69.dp)
                        .requiredHeight(72.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sidikjari),
                        contentDescription = "fingerprint",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 7.dp, y = 8.dp)
                            .requiredWidth(55.dp)
                            .requiredHeight(57.dp)
                    )
                }
            }

            Text(
                text = "Get in Touch!",
                color = Color.White,
                style = TextStyle(fontSize = 25.sp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 155.dp, y = 857.dp)
                    .requiredWidth(125.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    MaterialTheme {
        Welcome()
    }
}

