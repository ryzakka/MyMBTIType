// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityAura.kt
// [VERSI FINAL - EFEK "FROZEN VIRUS" YANG TAJAM DAN BERPENDAR]

package com.dhirekhaf.mytype

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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


// 🔮 Model dan utilitas interpolasi untuk sistem aura dinamis
// 🔮 Model dan utilitas interpolasi untuk sistem aura dinamis
data class PersonalitySpectrum(
    val e: Float, val s: Float, val t: Float, val j: Float
) {
    companion object {
        fun fromScores(scores: Map<Char, Int>): PersonalitySpectrum {
            return PersonalitySpectrum(
                e = ((scores['E'] ?: 50) / 100f).coerceIn(0f, 1f),
                s = ((scores['S'] ?: 50) / 100f).coerceIn(0f, 1f),
                t = ((scores['T'] ?: 50) / 100f).coerceIn(0f, 1f),
                j = ((scores['J'] ?: 50) / 100f).coerceIn(0f, 1f)
            )
        }

        // Fungsi interpolasi mandiri (tanpa tergantung Compose)
        fun lerp(start: Float, stop: Float, fraction: Float): Float {
            return start + (stop - start) * fraction
        }
    }

    // Fungsi turunan visual
    fun glowScale() = lerp(0.55f, 0.95f, e)
    fun glowAlpha() = lerp(0.25f, 0.85f, e)
    fun pointCount() = lerp(10f, 6f, 1f - s).toInt().coerceAtLeast(6)
    fun symmetry() = lerp(0.9f, 0.35f, s)
    fun sharpness() = lerp(0.85f, 0.18f, t)
    fun irregularity() = lerp(0.15f, 0.85f, 1f - j)
    fun rotationPeriod() = lerp(30000f, 14000f, 1f - j).toInt()
    fun pulsePeriod() = lerp(4200f, 2400f, 1f - j).toInt()
}



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
                color = Color.White)
            Spacer(Modifier.height(4.dp))
            Text(
                "Visualisasi unik berdasarkan skormu",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            //Spacer(Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
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
fun PersonalityAura(scores: Map<Char, Int>, theme: GroupTheme) {
    // Normalisasi skor 0..1 (dari skor 0..100)
    // Gunakan model baru untuk menurunkan parameter
    val spectrum = PersonalitySpectrum.fromScores(scores)

    val baseScale = spectrum.glowScale()
    val glowAlpha = spectrum.glowAlpha()
    val pointsCount = spectrum.pointCount()
    val symmetry = spectrum.symmetry()
    val sharpness = spectrum.sharpness()
    val irregularity = spectrum.irregularity()
    val rotationPeriod = spectrum.rotationPeriod()
    val pulsePeriod = spectrum.pulsePeriod()

    // Warna dasar dari theme (tetap gunakan theme agar konsisten)
    val primaryColor = theme.primaryColor
    val secondaryColor = theme.secondaryColor

    // Seed untuk variasi bentuk & partikel, tetap stabil selama pointsCount tidak berubah
    val shapeSeeds = remember(pointsCount) { List(pointsCount) { Random.nextFloat() } }
    val sparkleSeedCount = 28
    val sparkleSeeds = remember { List(sparkleSeedCount) { Random.nextFloat() } }

    // Animasi
    val infinite = rememberInfiniteTransition(label = "aura_enhanced_transition")
    val rotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(rotationPeriod, easing = LinearEasing), RepeatMode.Restart),
        label = "rotation"
    )
    val pulse by infinite.animateFloat(
        initialValue = 0.985f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(tween(pulsePeriod, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )
    // Wobble: perlahan mengubah fase variasi radius untuk kesan "bernafas" dan deformasi
    val wobblePhase by infinite.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(tween((pulsePeriod * 3), easing = LinearEasing), RepeatMode.Restart),
        label = "wobble"
    )

    // Sparkle animation driver (0..1 sin wave)
    val sparkleDriver by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(8000, easing = LinearEasing),
            RepeatMode.Restart),
        label = "sparkle"
    )

    // Draw on Canvas
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = this.center
        val minDim = size.minDimension
        val radius = (minDim / 2f) * baseScale

        // Layer A: Outer soft glow (besar, blur-like radial)
        drawAuraShape(
            drawScope = this,
            points = (pointsCount * 0.7f).toInt().coerceAtLeast(6),
            center = center,
            // 🔥 Baris 188
            baseRadius = radius * lerp(1.15f, 1.45f, spectrum.e) * pulse,
            seeds = shapeSeeds,
            symmetry = symmetry,
            sharpness = 0.12f, // lembut
            irregularity = irregularity * 0.45f,
            brush = Brush.radialGradient(
                colors = listOf(primaryColor.copy(alpha = glowAlpha * 0.9f), Color.Transparent),
                center = center,
                radius = minDim * 0.6f
            ),
            blendMode = BlendMode.Plus
        )

        // Layer B: Main cell membrane (inti — bentuk organik yang dipengaruhi tiap dimensi)
        rotate(degrees = rotation) {
            drawAuraShape(
                drawScope = this,
                points = pointsCount,
                center = center,
                baseRadius = radius * pulse,
                seeds = shapeSeeds,
                symmetry = symmetry,
                sharpness = sharpness,
                irregularity = irregularity,
                brush = Brush.radialGradient(
                    colors = listOf(
                        secondaryColor.copy(alpha = 0.95f),
                        primaryColor.copy(alpha = 0.35f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = minDim * 0.32f
                ),
                blendMode = BlendMode.Plus,
                wobblePhase = wobblePhase
            )
        }

        // Layer C: Inner glow / nucleus hint (lebih kecil, memberi kedalaman)
        drawCircle(
            brush = Brush.radialGradient(
                // 🔥 Baris 229
                colors = listOf(
                    Color.White.copy(alpha = 0.9f * (0.6f + (0.4f * (1 - spectrum.t)))),
                    primaryColor.copy(alpha = 0.05f)
                ),
                center = center,
                radius = minDim * 0.08f
            ),
            radius = minDim * 0.06f * pulse,
            center = center,
            alpha = 1f
        )

        // Layer D: Sparkles / particle kecil yang hidup (dipengaruhi oleh E/P & F/T)
        drawSparkles(
            drawScope = this,
            center = center,
            radius = radius,
            seeds = sparkleSeeds,
            driver = sparkleDriver,
            // 🔥 Baris 245
            intensity = lerp(0.25f, 1.0f, spectrum.e),
            irregularity = irregularity,
            sharpnessInfluence = sharpness,
            color = Color.White.copy(alpha = 0.9f)
        )

        // Optional subtle rim to make membrane terlihat (mengikuti symmetry / sharpness)
        drawAuraShape(
            drawScope = this,
            points = pointsCount,
            center = center,
            baseRadius = radius * 1.02f,
            seeds = shapeSeeds,
            symmetry = symmetry,
            sharpness = lerp(0.25f, 0.85f, sharpness),
            irregularity = irregularity * 0.6f,
            brush = Brush.radialGradient(
                colors = listOf(Color.White.copy(alpha = 0.12f), Color.Transparent),
                center = center,
                radius = minDim * 0.45f
            ),
            blendMode = BlendMode.Plus,
            strokeOnly = true
        )
    }
}

/**
 * Flexible draw function that produces an organic, cell-like closed shape.
 *
 * - drawScope: the DrawScope (pass "this" from Canvas)
 * - points: number of vertices
 * - center/baseRadius: defines size & position
 * - seeds: list of [0..1] floats used to vary radii
 * - symmetry: 0..1 -> higher = more symmetric; lower = more freeform
 * - sharpness: 0..1 -> how pointed are the bezier control influences
 * - irregularity: 0..1 -> scale of random radius deviation
 * - wobblePhase: optional animated phase (radians) to animate radius over time
 * - strokeOnly: if true, draw only a soft translucent stroke (no fill)
 */
private fun drawAuraShape(
    drawScope: DrawScope,
    points: Int,
    center: androidx.compose.ui.geometry.Offset,
    baseRadius: Float,
    seeds: List<Float>,
    symmetry: Float,
    sharpness: Float,
    irregularity: Float,
    brush: Brush,
    blendMode: BlendMode,
    wobblePhase: Float = 0f,
    strokeOnly: Boolean = false
) {
    if (points < 3) return
    with(drawScope) {
        val path = Path()
        val angleStep = (2 * PI / points).toFloat()

        // Build vertex list with controlled irregularity & symmetry
        val vertices = List(points) { i ->
            val angle = angleStep * i
            val seed = seeds.getOrElse(i) { 0.5f }
            // Symmetry reduces the effective randomization by blending with mirrored seed
            val mirroredIndex = (points - i) % points
            val mirroredSeed = seeds.getOrElse(mirroredIndex) { 0.5f }
            val combinedSeed = lerp(seed, mirroredSeed, symmetry)

            // Wobble introduces slow-time deformation
            val wobble = 1f + (0.08f * kotlin.math.sin(wobblePhase + i * 0.6f))

            val varied = baseRadius * (1f - irregularity * combinedSeed) * wobble
            Offset(
                x = center.x + kotlin.math.cos(angle) * varied,
                y = center.y + kotlin.math.sin(angle) * varied
            )
        }

        // Start at midpoint between last and first for smooth curve
        val start = Offset(
            (vertices.last().x + vertices.first().x) / 2f,
            (vertices.last().y + vertices.first().y) / 2f
        )
        path.moveTo(start.x, start.y)

        for (i in vertices.indices) {
            val cur = vertices[i]
            val next = vertices[(i + 1) % vertices.size]

            // Control point leans toward midpoint between cur and next based on sharpness
            val midX = (cur.x + next.x) / 2f
            val midY = (cur.y + next.y) / 2f

            val controlX = lerp(cur.x, midX, sharpness)
            val controlY = lerp(cur.y, midY, sharpness)

            path.quadraticBezierTo(cur.x, cur.y, controlX, controlY)
        }

        path.close()

        if (strokeOnly) {
            // Soft stroke: drawPath with blurred-like thin fill (alpha low)
            drawPath(path = path, brush = brush, style = androidx.compose.ui.graphics.drawscope.Fill, blendMode = blendMode)
        } else {
            drawPath(path = path, brush = brush, blendMode = blendMode)
        }
    }
}

/**
 * Draw sparkles / particles around the membrane.
 * Uses seed positions around the circle and applies small radial jitter.
 */
private fun drawSparkles(
    drawScope: DrawScope,
    center: androidx.compose.ui.geometry.Offset,
    radius: Float,
    seeds: List<Float>,
    driver: Float, // 0..1 animation driver
    intensity: Float, // overall multiplier
    irregularity: Float,
    sharpnessInfluence: Float,
    color: Color
) {
    with(drawScope) {
        val count = seeds.size
        val minDim = size.minDimension
        for (i in 0 until count) {
            val seed = seeds[i]
            // Base angle around circle + some spread
            val angle = (2 * PI * seed).toFloat() + (driver * 2f * PI.toFloat() * (0.2f + seed))
            // Radial distance slightly beyond the membrane, modulated by irregularity and driver
            val spread = lerp(0.06f, 0.22f, irregularity) * radius
            val radialJitter = spread * (0.4f + seed * 0.8f) * (0.6f + 0.4f * driver)
            val px = center.x + kotlin.math.cos(angle) * (radius + radialJitter)
            val py = center.y + kotlin.math.sin(angle) * (radius + radialJitter)

            // Sparkle size & alpha depend on driver and intensity and a little on sharpnessInfluence
            val baseSize = lerp(minDim * 0.0045f, minDim * 0.018f, seed)
            val size = baseSize * (0.6f + 0.8f * intensity * (0.3f + driver))
            val alpha = (0.12f + 0.7f * intensity * driver * (0.5f + seed * 0.5f)) * (0.5f + 0.5f * sharpnessInfluence)

            // Draw small glow (two concentric circles for subtle bloom)
            drawCircle(
                color = color.copy(alpha = alpha.coerceIn(0f, 1f)),
                radius = size,
                center = Offset(px, py),
                blendMode = BlendMode.Plus
            )
            drawCircle(
                color = color.copy(alpha = (alpha * 0.35f).coerceIn(0f, 1f)),
                radius = size * 2.6f,
                center = Offset(px, py),
                blendMode = BlendMode.Plus
            )
        }
    }
}
