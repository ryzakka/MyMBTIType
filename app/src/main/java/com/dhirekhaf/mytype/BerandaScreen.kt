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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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

// --- BAGIAN 1: STRUKTUR DATA & TEMA ---
// Mendefinisikan properti visual untuk setiap grup kepribadian.
data class PersonalityTheme(
    @DrawableRes val background: Int,
    val particleColor: Color,
    val gemColors: List<Color>
)

// Memetakan setiap grup kepribadian ke tema visualnya.
val personalityThemes = mapOf(
    "Analyst" to PersonalityTheme(R.drawable.latarbodyungu, Color(0xFFD0BCFF), listOf(Color(0xFF623A86), Color(0xFFD0BCFF))),
    "Diplomat" to PersonalityTheme(R.drawable.latarbodyhijau, Color(0xFFC3F3D0), listOf(Color(0xFF2E6B4F), Color(0xFFC3F3D0))),
    "Sentinel" to PersonalityTheme(R.drawable.latarbodybiru, Color(0xFFA5D8FF), listOf(Color(0xFF004A7F), Color(0xFFA5D8FF))),
    "Explorer" to PersonalityTheme(R.drawable.latarbodykuning, Color(0xFFFFE082), listOf(Color(0xFFB8860B), Color(0xFFFFE082))),
    "Default" to PersonalityTheme(R.drawable.latarponihijau, Color.White, listOf(Color.White, Color.Gray))
)

// Fungsi utilitas untuk mendapatkan tema visual berdasarkan tipe MBTI.
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


// --- BAGIAN 2: GAYA TEKS (TEXT STYLES) ---
// Mendefinisikan gaya teks yang akan digunakan di layar ini agar konsisten.
private val BerandaGayaSapaan = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
private val BerandaGayaKutipan = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center, lineHeight = 30.sp, color = Color.White.copy(0.9f))
private val MbtiConstellationStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)


// --- BAGIAN 3: COMPOSABLE UTAMA ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaScreen(
    navController: NavController,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Mengambil data pengguna dari ViewModel.
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by meViewModel.userData.collectAsState()

    // State untuk mengontrol dialog konfirmasi "Retake Test".
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

    // State untuk mengontrol bottom sheet yang menampilkan detail dimensi.
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

    // Struktur utama layar menggunakan Box untuk menumpuk elemen.
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


// --- BAGIAN 4: HEADER UTAMA LAYAR BERANDA ---
@Composable
private fun BerandaHeaderElegan(
    modifier: Modifier = Modifier,
    userData: UserData,
    onProfileClick: () -> Unit
) {
    val (name, mbtiType, imageUri) = userData
    val displayName = if (name.isNotBlank()) name else "Beautiful Human!"
    // State untuk kutipan, akan berubah jika tipe MBTI berubah atau di-tap.
    var currentQuote by remember(mbtiType) {
        mutableStateOf(
            (mbtiQuotes[mbtiType]?.random() ?: generalQuotes.random())
        )
    }

    // State dan LaunchedEffect untuk mengatur animasi fade-in.
    var showStaticHeader by remember { mutableStateOf(false) }
    var showDynamicContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        showStaticHeader = true
        delay(400)
        showDynamicContent = true
    }

    // State animasi untuk alpha (transparansi) dan offset (posisi).
    val headerAlpha by animateFloatAsState(targetValue = if (showStaticHeader) 1f else 0f, animationSpec = tween(800), label = "h_alpha")
    val contentAlpha by animateFloatAsState(targetValue = if (showDynamicContent) 1f else 0f, animationSpec = tween(1000, 200), label = "c_alpha")
    val contentOffsetY by animateDpAsState(targetValue = if (showDynamicContent) 0.dp else 20.dp, animationSpec = tween(1000, 200), label = "c_offset")

    // Animasi "Ken Burns" (zoom perlahan) untuk gambar latar.
    val infiniteTransition = rememberInfiniteTransition("inf_trans")
    val scale by infiniteTransition.animateFloat(1.0f, 1.1f, infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Reverse), "ken_burns")

    // Mendapatkan tema visual berdasarkan tipe MBTI pengguna.
    val theme = getThemeForMbti(mbtiType)

    Box(modifier.fillMaxSize().clip(RoundedCornerShape(0.dp))) {
        // Latar belakang dengan partikel dan gradien.
        Box(Modifier.fillMaxSize()) {
            Crossfade(theme.background, animationSpec = tween(1500), label = "bg_crossfade") { imageRes ->
                Image(painterResource(imageRes), null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().scale(scale))
            }
            FloatingParticles(theme.particleColor)
            Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Black.copy(0.6f), Color.Transparent, Color.Black.copy(0.8f)))))

            // Area untuk menampilkan kutipan yang bisa di-tap.
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 220.dp, start = 48.dp, end = 48.dp)
                    .graphicsLayer { alpha = contentAlpha; translationY = contentOffsetY.toPx() }
                    .pointerInput(Unit) { detectTapGestures(onTap = { currentQuote = (mbtiQuotes[mbtiType]?.random() ?: generalQuotes.random()) }) },
                contentAlignment = Alignment.Center
            ) {
                Crossfade(currentQuote, animationSpec = tween(500), label = "quote_crossfade") { quoteText -> Text("\"$quoteText\"", style = BerandaGayaKutipan) }
            }
        }

        // Baris atas yang berisi sapaan "Hi, Nama" dan gambar profil.
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


// --- BAGIAN 5: PANGGUNG CERITA (AREA BAWAH) ---
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StoryStage(
    modifier: Modifier = Modifier,
    mbtiType: String,
    onTakeTestClick: () -> Unit,
    onRetakeTest: () -> Unit,
    onGemstoneClick: (Char, String) -> Unit
) {
    // Mengecek apakah pengguna sudah pernah tes atau belum.
    val hasTakenTest = mbtiType.isNotEmpty()
    var stageVisible by remember { mutableStateOf(false) }

    // Animasi slide-in dari bawah saat pertama kali muncul.
    LaunchedEffect(Unit) {
        delay(800)
        stageVisible = true
    }

    AnimatedVisibility(
        visible = stageVisible,
        enter = slideInVertically(animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow), initialOffsetY = { it }) + fadeIn(tween(1000)),
        modifier = modifier.fillMaxWidth()
    ) {
        // Box ini adalah container utama untuk area bawah.
        Box(
            contentAlignment = Alignment.BottomCenter,
            // Padding bawah diatur untuk memberi ruang pada BottomNavBar.
            modifier = Modifier.padding(bottom = 160.dp)
        ) {
            // Latar belakang semi-transparan untuk panggung.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.1f), Color.White.copy(alpha = 0.05f), Color.Transparent)))
                    .border(width = 1.dp, brush = Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)), shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            )

            // AnimatedContent untuk beralih antara tampilan "Take Test" dan "Constellation".
            AnimatedContent(
                targetState = hasTakenTest,
                transitionSpec = { fadeIn(tween(1000, 500)) togetherWith fadeOut(tween(500)) },
                label = "stage_content"
            ) { hasTestResult ->
                if (hasTestResult) {
                    MbtiConstellation(mbtiType = mbtiType, onGemstoneClick = onGemstoneClick, onRetakeTest = onRetakeTest)
                } else {
                    TakeTestPrompt(onTakeTestClick = onTakeTestClick)
                }
            }
        }
    }
}


// --- BAGIAN 6: TAMPILAN KONSTELASI MBTI ---
@Composable
fun MbtiConstellation(
    mbtiType: String,
    onGemstoneClick: (Char, String) -> Unit,
    onRetakeTest: () -> Unit
) {
    val theme = getThemeForMbti(mbtiType)
    val dimensions = listOf(mbtiType[0] to "Mind", mbtiType[1] to "Energy", mbtiType[2] to "Nature", mbtiType[3] to "Tactics")

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = mbtiType, style = MbtiConstellationStyle, color = theme.particleColor)
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
            dimensions.forEach { (char, explanation) ->
                Gemstone(label = char, colors = theme.gemColors, onClick = { onGemstoneClick(char, explanation) })
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Retake Test", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, modifier = Modifier.clickable(onClick = onRetakeTest))
    }
}


// --- BAGIAN 7: TAMPILAN AJAKAN UNTUK TES ---
@Composable
private fun TakeTestPrompt(onTakeTestClick: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("What's your type?", style = MaterialTheme.typography.titleLarge, color = Color.White)
        Spacer(Modifier.height(8.dp))
        Text("Take our test to discover your personality type and get personalized insights.", textAlign = TextAlign.Center, color = Color.White.copy(alpha = 0.8f))
        Spacer(Modifier.height(24.dp))
        Button(onClick = onTakeTestClick, shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color.White), contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Take the Test", color = Color.Black, fontWeight = FontWeight.Bold)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Black)
            }
        }
    }
}


// --- BAGIAN 8: ELEMEN VISUAL (BATU PERMATA & PARTIKEL) ---
@Composable
fun Gemstone(label: Char, colors: List<Color>, onClick: () -> Unit) {
    // Animasi cahaya berkelip dan skala membesar/mengecil.
    val infiniteTransition = rememberInfiniteTransition(label = "gem_glow")
    val alpha by infiniteTransition.animateFloat(initialValue = 0.5f, targetValue = 1.0f, animationSpec = infiniteRepeatable(animation = tween(durationMillis = 2000 + Random.nextInt(1000), easing = LinearEasing), repeatMode = RepeatMode.Reverse), label = "gem_alpha")
    val scale by infiniteTransition.animateFloat(initialValue = 0.95f, targetValue = 1.05f, animationSpec = infiniteRepeatable(animation = tween(durationMillis = 3000 + Random.nextInt(1000), easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse), label = "gem_scale")

    Box(
        modifier = Modifier
            .size(60.dp)
            .scale(scale)
            .alpha(alpha)
            .clip(RoundedCornerShape(30))
            .background(Brush.radialGradient(colors))
            .border(1.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(30))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label.toString(), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FloatingParticles(particleColor: Color) {
    // Menggambar partikel-partikel kecil yang bergerak ke atas secara acak.
    val particles = remember { List(30) { Particle() } }
    val infiniteTransition = rememberInfiniteTransition(label = "particle_transition")
    val animationTime by infiniteTransition.animateFloat(initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Restart), label = "particles_time")
    val density = LocalDensity.current.density

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val currentY = size.height * (1f - ((animationTime + particle.startTime) % 1f))
            drawCircle(color = particleColor, radius = particle.size * density, center = androidx.compose.ui.geometry.Offset(particle.startX * size.width, currentY), alpha = particle.alpha)
        }
    }
}

// Data class untuk menyimpan properti setiap partikel.
data class Particle(
    val startX: Float = Random.nextFloat(),
    val startTime: Float = Random.nextFloat(),
    val size: Float = Random.nextFloat() * 2f + 1f,
    val alpha: Float = Random.nextFloat() * 0.5f + 0.2f
)


// --- BAGIAN 9: DIALOG & BOTTOM SHEET ---
@Composable
fun ConfirmRetakeTestDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Retake Test?") },
        text = { Text("Your previous result will be overwritten. Are you sure?") },
        confirmButton = { Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) { Text("Confirm") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun DimensionDetailSheet(dimension: Pair<Char, String>) {
    val (char, title) = dimension
    val explanation = when(char) {
        'E' -> "Extraversion: Mendapatkan energi dari interaksi sosial dan dunia luar."
        'I' -> "Introversion: Mendapatkan energi dari refleksi diri dan dunia internal."
        'S' -> "Sensing: Fokus pada fakta konkret, detail, dan pengalaman saat ini."
        'N' -> "Intuition: Fokus pada pola, kemungkinan, dan makna di balik informasi."
        'T' -> "Thinking: Membuat keputusan berdasarkan logika, objektivitas, dan analisis."
        'F' -> "Feeling: Membuat keputusan berdasarkan nilai-nilai pribadi, empati, dan harmoni."
        'J' -> "Judging: Lebih suka kehidupan yang terstruktur, terencana, dan terorganisir."
        'P' -> "Perceiving: Lebih suka kehidupan yang fleksibel, spontan, dan terbuka pada pilihan."
        else -> "Dimensi tidak diketahui."
    }

    Column(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = explanation, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}
