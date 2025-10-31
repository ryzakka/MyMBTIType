// File: app/src/main/java/com/dhirekhaf/mytype/CommonUI.kt

package com.dhirekhaf.mytype

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random


data class PersonalityTheme(
    @DrawableRes val background: Int,
    val particleColor: Color,
    val gemColors: List<Color>
)

val personalityThemes = mapOf(
    "Analyst" to PersonalityTheme(R.drawable.latarbodyungu, Color(0xFFD0BCFF), listOf(Color(0xFF623A86), Color(0xFFD0BCFF))),
    "Diplomat" to PersonalityTheme(R.drawable.latarbodyhijau, Color(0xFFC3F3D0), listOf(Color(0xFF2E6B4F), Color(0xFFC3F3D0))),
    "Sentinel" to PersonalityTheme(R.drawable.latarbodybiru, Color(0xFFA5D8FF), listOf(Color(0xFF004A7F), Color(0xFFA5D8FF))),
    "Explorer" to PersonalityTheme(R.drawable.latarbodykuning, Color(0xFFFFE082), listOf(Color(0xFFB8860B), Color(0xFFFFE082))),
    "Default" to PersonalityTheme(R.drawable.latarponihijau, Color.White, listOf(Color.White, Color.Gray))
)

fun getThemeForMbti(mbtiType: String): PersonalityTheme {
    val group = when (mbtiType) {
        "INTJ", "INTP", "ENTJ", "ENTP" -> "Analyst"
        "INFJ", "INFP", "ENFJ", "ENFP" -> "Diplomat"
        "ISTJ", "ISFJ", "ESTJ", "ESFJ" -> "Sentinel"
        "ISTP", "ISFP", "ESTP", "ESFP" -> "Explorer"
        else -> "Default"
    }
    return personalityThemes[group]!!
}

// --- BAGIAN 2: KOMPONEN VISUAL BERSAMA ---

data class FloatingParticle(
    val startX: Float = Random.nextFloat(),
    val startTime: Float = Random.nextFloat(),
    val size: Float = Random.nextFloat() * 2f + 1f,
    val alpha: Float = Random.nextFloat() * 0.5f + 0.2f
)

@Composable
fun FloatingParticles(particleColor: Color) {
    val particles = remember { List(30) { FloatingParticle() } }
    val infiniteTransition = rememberInfiniteTransition(label = "particle_transition")
    val animationTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Restart),
        label = "particles_time"
    )
    val density = LocalDensity.current.density

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val currentY = size.height * (1f - ((animationTime + particle.startTime) % 1f))
            drawCircle(
                color = particleColor,
                radius = particle.size * density,
                center = Offset(particle.startX * size.width, currentY),
                alpha = particle.alpha
            )
        }
    }
}

// Gemstone/citra
@Composable
fun Gemstone(label: Char, colors: List<Color>, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "gem_glow_${label}")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500 + Random.nextInt(500), easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "gem_alpha"
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500 + Random.nextInt(1000), easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "gem_scale"
    )

    Box(
        modifier = Modifier
            .size(64.dp)
            .scale(scale)
            .alpha(alpha)
            .clip(RoundedCornerShape(30))
            .background(Brush.radialGradient(colors))
            .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(30))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label.toString(),
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium.copy(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            )
        )
    }
}


// --- BAGIAN 3: BOTTOM SHEET & NAVIGASI ---
private val dimensionDetails = mapOf(
    'I' to "Introvert (I) berfokus pada dunia internal pikiran dan ide. Mereka mendapatkan energi dari waktu sendiri dan refleksi.",
    'E' to "Ekstrovert (E) berfokus pada dunia eksternal, orang, dan aktivitas. Mereka mendapatkan energi dari interaksi sosial.",
    'N' to "Intuition (N) berfokus pada pola, kemungkinan, dan makna abstrak. Mereka melihat gambaran besar dan masa depan.",
    'S' to "Sensing (S) berfokus pada informasi konkret dan faktual yang diterima melalui panca indera. Mereka praktis dan berorientasi pada saat ini.",
    'F' to "Feeling (F) membuat keputusan berdasarkan nilai-nilai pribadi, empati, dan bagaimana dampaknya pada orang lain.",
    'T' to "Thinking (T) membuat keputusan berdasarkan logika, objektivitas, dan analisis impersonal.",
    'P' to "Perceiving (P) lebih suka gaya hidup yang fleksibel, spontan, dan terbuka terhadap informasi baru.",
    'J' to "Judging (J) lebih suka gaya hidup yang terstruktur, terorganisir, dan berorientasi pada keputusan."
)

//Composable BottomSheet.
@Composable
fun DimensionDetailSheet(dimension: Pair<Char, String>) {
    val (char, title) = dimension
    val explanation = dimensionDetails[char] ?: "Dimensi tidak diketahui."

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = explanation,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun ModernBottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "beranda"
        ),
        BottomNavItem(
            title = "Personalities",
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            route = "personalities"
        ),
        BottomNavItem(
            title = "Favorites",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.StarBorder,
            route = "favorites"
        ),
        BottomNavItem(
            title = "Me",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "me"
        )
    )

    if (currentRoute in items.map { it.route }) {
        NavigationBar(
            modifier = modifier,
            containerColor = Color.White,
            contentColor = Color(0xff3f414e)
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            onNavigate(item.route)
                        }
                    },
                    label = { Text(text = item.title) },
                    icon = {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xff47AD82),
                        selectedTextColor = Color(0xff47AD82),
                        indicatorColor = Color(0xff47AD82).copy(alpha = 0.1f),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    }
}

@Composable
fun AddFavoriteLabelDialog(
    currentLabel: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var label by remember { mutableStateOf(currentLabel) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Beri Label Favorit", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Beri nama untuk menandai hubungan Anda dengan tipe ini (Contoh: Teman Baik, Ibu, Partner).")
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Label") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(label.ifBlank { "Favorit" }) },
            ) {
                Text("Simpan", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
