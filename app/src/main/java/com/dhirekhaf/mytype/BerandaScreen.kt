// File: app/src/main/java/com/dhirekhaf/mytype/BerandaScreen.kt

package com.dhirekhaf.mytype

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
// --- [PERBAIKAN] HAPUS IMPORT YANG TIDAK DIGUNAKAN DAN SALAH ---
// import androidx.compose.material.icons.Icons (ini akan diganti dengan import yang lebih spesifik)
import androidx.compose.material.icons.Icons // <-- Import ini tetap dibutuhkan untuk mengakses sub-paket
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight // <-- [PERBAIKAN] IMPORT YANG BENAR UNTUK IKON PANAH
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dhirekhaf.mytype.data.UserData
import com.dhirekhaf.mytype.data.UserDataRepository
import com.dhirekhaf.mytype.data.generalQuotes
import com.dhirekhaf.mytype.data.mbtiQuotes
import kotlinx.coroutines.delay
import kotlin.random.Random

// --- Struktur Data Tema tidak berubah ---
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

private val BerandaGayaSapaan = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
private val BerandaGayaKutipan = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center, lineHeight = 30.sp, color = Color.White.copy(0.9f))
private val MbtiConstellationStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaScreen(
    navController: NavController,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))

    val userData by meViewModel.userData.collectAsState()

    var showConfirmDialog by remember { mutableStateOf(false) }
    if (showConfirmDialog) {
        ConfirmRetakeTestDialog(
            onConfirm = {
                showConfirmDialog = false
                meViewModel.resetMbtiTest()
                navController.navigate("personality_test")
            },
            onDismiss = { showConfirmDialog = false }
        )
    }

    val sheetState = rememberModalBottomSheetState()
    var selectedDimension by remember { mutableStateOf<Pair<Char, String>?>(null) }

    if (selectedDimension != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedDimension = null },
            sheetState = sheetState
        ) {
            DimensionDetailSheet(dimension = selectedDimension!!)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        BerandaHeaderElegan(
            userData = userData,
            onProfileClick = { onNavigate("me") }
        )

        StoryStage(
            modifier = Modifier.align(Alignment.BottomCenter),
            mbtiType = userData.mbtiType,
            onTakeTestClick = { navController.navigate("personality_test") },
            onGemstoneClick = { dimension, explanation ->
                selectedDimension = dimension to explanation
            },
            onRetakeTest = { showConfirmDialog = true }
        )

        ModernBottomNavBar(
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun BerandaHeaderElegan(
    modifier: Modifier = Modifier,
    userData: UserData,
    onProfileClick: () -> Unit
) {
    val (name, mbtiType, imageUri) = userData
    val displayName = if (name.isNotBlank()) name else "Beautiful Human!"
    var currentQuote by remember(mbtiType) {
        mutableStateOf(
            (mbtiQuotes[mbtiType]?.random() ?: generalQuotes.random())
        )
    }

    var showStaticHeader by remember { mutableStateOf(false) }
    var showDynamicContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        showStaticHeader = true
        delay(400)
        showDynamicContent = true
    }

    val headerAlpha by animateFloatAsState(
        targetValue = if (showStaticHeader) 1f else 0f,
        animationSpec = tween(800),
        label = "h_alpha"
    )
    val contentAlpha by animateFloatAsState(
        targetValue = if (showDynamicContent) 1f else 0f,
        animationSpec = tween(1000, 200),
        label = "c_alpha"
    )
    val contentOffsetY by animateDpAsState(
        targetValue = if (showDynamicContent) 0.dp else 20.dp,
        animationSpec = tween(1000, 200),
        label = "c_offset"
    )
    val infiniteTransition = rememberInfiniteTransition("inf_trans")
    val scale by infiniteTransition.animateFloat(1.0f, 1.1f, infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Reverse), "ken_burns")
    val theme = getThemeForMbti(mbtiType)

    Box(modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(0.dp))) {
        Box(Modifier.fillMaxSize()) {
            Crossfade(theme.background, animationSpec = tween(1500), label = "bg_crossfade") { imageRes ->
                Image(painterResource(imageRes), null, contentScale = ContentScale.Crop, modifier = Modifier
                    .fillMaxSize()
                    .scale(scale))
            }
            FloatingParticles(theme.particleColor)
            Box(Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(0.6f),
                            Color.Transparent,
                            Color.Black.copy(0.8f)
                        )
                    )
                ))

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 220.dp, start = 48.dp, end = 48.dp)
                    .graphicsLayer { alpha = contentAlpha; translationY = contentOffsetY.toPx() }
                    .pointerInput(Unit) { detectTapGestures(onTap = { currentQuote =
                        (mbtiQuotes[mbtiType]?.random() ?: generalQuotes.random())
                    })
                    },
                contentAlignment = Alignment.Center
            ) {
                Crossfade(currentQuote, animationSpec = tween(500), label = "quote_crossfade") { quoteText -> Text("\"$quoteText\"", style = BerandaGayaKutipan) }
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .alpha(headerAlpha),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Hi, $displayName", style = BerandaGayaSapaan)
            AsyncImage(
                model = imageUri ?: R.drawable.sidikjari,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
                    .clickable(onClick = onProfileClick),
                contentScale = ContentScale.Crop
            )

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StoryStage(
    modifier: Modifier = Modifier,
    mbtiType: String,
    onTakeTestClick: () -> Unit,
    onRetakeTest: () -> Unit,
    onGemstoneClick: (Char, String) -> Unit
) {
    val hasTakenTest = mbtiType.isNotEmpty()
    var stageVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(800)
        stageVisible = true
    }

    AnimatedVisibility(
        visible = stageVisible,
        enter = slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            ),
            initialOffsetY = { it }
        ) + fadeIn(tween(1000)),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.padding(bottom = 80.dp) // Jarak agar tidak tertutup NavBar
        ) {
            // --- PANGGUNG MODERN ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.1f),
                                Color.White.copy(alpha = 0.05f),
                                Color.Transparent
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
            )

            AnimatedContent(
                targetState = hasTakenTest,
                transitionSpec = {
                    fadeIn(tween(1000, 1000)) togetherWith fadeOut(tween(500))
                },
                modifier = Modifier.padding(bottom = 16.dp),
                label = "StoryStageContent"
            ) { hasTestResult ->
                if (hasTestResult) {
                    AfterTestView(mbtiType = mbtiType, onGemstoneClick = onGemstoneClick, onRetakeTest = onRetakeTest)
                } else {
                    BeforeTestView(onTakeTestClick = onTakeTestClick)
                }
            }
        }
    }
}

@Composable
fun BeforeTestView(onTakeTestClick: () -> Unit) {
    var contentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(200) // Sedikit delay agar animasi panggung utama selesai
        contentVisible = true
    }

    // Gunakan Box agar kita bisa menumpuk elemen dengan bebas
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp), // Sesuaikan tinggi Box agar pas
        contentAlignment = Alignment.Center
    ) {
        // --- PERUBAHAN UTAMA DI SINI ---
        // Tombol yang didesain ulang, diletakkan di tengah Box
        AnimatedVisibility(
            visible = contentVisible,
            enter = scaleIn(animationSpec = tween(700, delayMillis = 200), initialScale = 0.8f) +
                    fadeIn(animationSpec = tween(700, delayMillis = 200))
        ) {
            Button(
                onClick = onTakeTestClick,
                shape = RoundedCornerShape(50), // Bentuk pil yang modern
                modifier = Modifier
                    .width(IntrinsicSize.Max) // Lebar tombol menyesuaikan kontennya
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // Container transparan agar gradien terlihat
                ),
                contentPadding = PaddingValues(), // Hapus padding default
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                // Latar belakang gradien untuk tombol
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF623A86), Color(0xFF8A65A8))
                            )
                        )
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Mulai Perjalananmu",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // <-- [PERBAIKAN] Menggunakan path yang benar
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AfterTestView(mbtiType: String, onGemstoneClick: (Char, String) -> Unit, onRetakeTest: () -> Unit) {
    val dimensionDetails = mapOf(
        'E' to "Extraversion: Anda mendapatkan energi dari interaksi sosial.", 'I' to "Introversion: Anda mendapatkan energi dari waktu menyendiri.",
        'S' to "Sensing: Anda fokus pada fakta dan kenyataan.", 'N' to "Intuition: Anda fokus pada pola dan kemungkinan.",
        'T' to "Thinking: Anda membuat keputusan berdasarkan logika.", 'F' to "Feeling: Anda membuat keputusan berdasarkan nilai dan perasaan.",
        'J' to "Judging: Anda lebih menyukai kehidupan yang terstruktur.", 'P' to "Perceiving: Anda lebih menyukai kehidupan yang spontan."
    )
    val theme = getThemeForMbti(mbtiType)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MbtiTypeConstellation(mbtiType = mbtiType)
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            mbtiType.forEachIndexed { index, char ->
                val (dimension, explanation) = dimensionDetails.entries.find { it.key == char }!!
                Gemstone(
                    color = theme.gemColors[0],
                    glowColor = theme.gemColors[1],
                    label = char.toString(),
                    delay = index * 200,
                    onClick = { onGemstoneClick(dimension, explanation) }
                )
            }
        }
        TextButton(onClick = onRetakeTest, modifier = Modifier.padding(top = 8.dp)) {
            Text("Ulangi Tes", color = Color.White.copy(0.7f), fontSize = 12.sp)
        }
    }
}

@Composable
fun MbtiTypeConstellation(mbtiType: String) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(mbtiType) {
        delay(500)
        visible = true
    }
    AnimatedVisibility(visible, enter = fadeIn(tween(1000, 500))) {
        // --- TEKS MODERN DENGAN SHADOW ---
        Text(
            text = mbtiType,
            style = MbtiConstellationStyle,
            modifier = Modifier.graphicsLayer {
                shadowElevation = 8.dp.toPx()
            }
        )
    }
}

@Composable
fun Gemstone(color: Color, glowColor: Color, label: String, delay: Int, onClick: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(delay.toLong() + 500)
        visible = true
    }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "gem_scale"
    )

    // --- PERMATA MODERN (LINGKARAN BERSINAR) ---
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clickable(onClick = onClick)
            .size(50.dp) // Beri ukuran yang pasti
    ) {
        // Latar belakang cahaya (glow) yang halus
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(glowColor.copy(alpha = 0.5f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        // Lingkaran utama
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .background(color.copy(alpha = 0.8f))
                .border(1.dp, glowColor, CircleShape)
        )

        // Teks label di tengah
        Text(label, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun DimensionDetailSheet(dimension: Pair<Char, String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(dimension.second.substringBefore(':'), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text("(${dimension.first})", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
        Spacer(Modifier.height(16.dp))
        Text(dimension.second.substringAfter(':').trim(), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}

@Composable
fun FloatingParticles(particleColor: Color) {
    val particles = remember { List(50) { Particle(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 0.00005f + 0.00002f, Random.nextFloat() * 1.5f + 0.5f, Random.nextFloat() * 0.5f + 0.2f) } }
    val infiniteTransition = rememberInfiniteTransition("particle_trans")
    val progress by infiniteTransition.animateFloat(0f, 1f, infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Restart), "particle_prog")
    val density = LocalDensity.current.density
    Canvas(Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val currentY = (particle.y - progress * particle.speed * size.height / density) % 1f
            drawCircle(particleColor, particle.size * density, androidx.compose.ui.geometry.Offset(particle.x * size.width, currentY * size.height), particle.alpha)
        }
    }
}

data class Particle(val x: Float, val y: Float, val speed: Float, val size: Float, val alpha: Float)

@Composable
private fun ConfirmRetakeTestDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ulangi Perjalanan?", fontWeight = FontWeight.Bold) },
        text = { Text("Apakah Anda yakin? Hasil sebelumnya akan dihapus dan Anda akan memulai tes dari awal.") },
        confirmButton = { TextButton(onClick = onConfirm, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)) { Text("Ya, Ulangi", fontWeight = FontWeight.Bold) } },
        dismissButton = { TextButton(onClick = onDismiss, colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)) { Text("Batal") } },
        shape = RoundedCornerShape(20.dp)
    )
}
