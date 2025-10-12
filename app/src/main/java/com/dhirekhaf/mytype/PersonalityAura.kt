// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityAura.kt
// [VERSI FINAL - BENTUK ORGANIK DENGAN KURVA STABIL]

package com.dhirekhaf.mytype

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Fungsi utama Card (tidak berubah)
@Composable
fun AuraCard(scores: Map<Char, Int>, theme: GroupTheme) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Aura Kepribadian",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Visualisasi unik berdasarkan skormu",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                PersonalityAura(scores = scores, theme = theme)
            }
        }
    }
}

// Composable inti yang menggambar Aura
@Composable
private fun PersonalityAura(scores: Map<Char, Int>, theme: GroupTheme) {
    val iVsE = (scores['E'] ?: 50) / 100f
    val sVsN = (scores['S'] ?: 50) / 100f
    val tVsF = (scores['T'] ?: 50) / 100f
    val jVsP = (scores['J'] ?: 50) / 100f

    val auraSizeFactor = lerp(0.6f, 0.9f, iVsE)
    val pointsCount = lerp(4f, 12f, sVsN).toInt()
    val sharpness = lerp(0.1f, 0.8f, tVsF) // Thinking = lebih tajam
    val asymmetry = lerp(0.4f, 0.0f, jVsP) // Judging = lebih simetris

    val infiniteTransition = rememberInfiniteTransition(label = "aura_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(30000, easing = LinearEasing), RepeatMode.Restart),
        label = "rotation"
    )
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(tween(4000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    val primaryColor = theme.primaryColor
    val secondaryColor = theme.secondaryColor

    val randomSeeds = remember(pointsCount) { List(pointsCount) { 1.0f - asymmetry * Random.nextFloat() } }

    Canvas(modifier = Modifier.fillMaxSize()) {
        rotate(rotation) {
            drawAuraShape(
                points = pointsCount,
                sizeFactor = auraSizeFactor * pulse,
                sharpness = sharpness,
                brush = Brush.radialGradient(listOf(primaryColor.copy(alpha = 0.5f), Color.Transparent)),
                seeds = randomSeeds
            )
        }
        rotate(rotation * -0.7f + 30f) {
            drawAuraShape(
                points = pointsCount,
                sizeFactor = auraSizeFactor * pulse * 0.7f,
                sharpness = sharpness,
                brush = Brush.radialGradient(listOf(secondaryColor.copy(alpha = 0.7f), Color.Transparent)),
                seeds = randomSeeds.reversed()
            )
        }
    }
}

// vvv [MENGEMBALIKAN KURVA BEZIER DENGAN AMAN] vvv
private fun DrawScope.drawAuraShape(
    points: Int,
    sizeFactor: Float,
    sharpness: Float, // Parameter ini sekarang tidak digunakan di versi stabil ini, tapi bisa untuk masa depan
    brush: Brush,
    seeds: List<Float>
) {
    if (points < 3) return

    val path = Path()
    val radius = (size.minDimension / 2) * sizeFactor
    val angleStep = (2 * PI / points).toFloat()

    // Buat daftar semua titik sudut terlebih dahulu
    val vertices = List(points) { i ->
        val angle = angleStep * i
        val currentRadius = radius * seeds.getOrElse(i) { 1f }
        Offset(
            x = center.x + (cos(angle) * currentRadius),
            y = center.y + (sin(angle) * currentRadius)
        )
    }

    // Mulai path dari titik tengah antara titik terakhir dan pertama
    val startPoint = Offset(
        (vertices.last().x + vertices.first().x) / 2,
        (vertices.last().y + vertices.first().y) / 2
    )
    path.moveTo(startPoint.x, startPoint.y)

    // Loop untuk menggambar kurva ke setiap titik sudut
    for (i in 0 until points) {
        val currentVertex = vertices[i]
        val nextVertex = vertices[(i + 1) % points] // Kembali ke titik pertama di akhir
        // Titik kontrol adalah titik tengah antara dua titik sudut
        val controlPointX = (currentVertex.x + nextVertex.x) / 2
        val controlPointY = (currentVertex.y + nextVertex.y) / 2

        // Menggunakan quadraticBezierTo untuk kurva yang lebih sederhana dan stabil
        path.quadraticBezierTo(
            currentVertex.x,
            currentVertex.y,
            controlPointX,
            controlPointY
        )
    }

    path.close()

    drawPath(
        path = path,
        brush = brush,
        blendMode = BlendMode.Plus
    )
}
