// Lokasi: app/src/main/java/com/dhirekhaf/mytype/BerandaScreen.kt
// [BERSIH & STABIL] - Menggunakan Image Background dan FloatingParticles dari CommonUI.kt

package com.dhirekhaf.mytype

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.dhirekhaf.mytype.ui.theme.MyTypeTheme
import kotlinx.coroutines.delay


// --- GAYA TEKS ---
private val BerandaGayaSapaan = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
private val BerandaGayaKutipan = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center, lineHeight = 30.sp, color = Color.White.copy(0.9f))
private val MbtiConstellationStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold) // Warna diatur di Composable

// --- COMPOSABLE UTAMA ---
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
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
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


// --- KOMPONEN-KOMPONEN KHUSUS BERANDA ---

@Composable
fun ConfirmRetakeTestDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    MyTypeTheme {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Ulangi Tes?", fontWeight = FontWeight.Bold) },
            text = { Text("Hasil tes Anda saat ini akan dihapus. Anda yakin ingin melanjutkan?") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Ya, Ulangi", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Batal")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun BerandaHeaderElegan(
    modifier: Modifier = Modifier,
    userData: UserData,
    onProfileClick: () -> Unit
) {
    val displayName = if (userData.name.isNotBlank()) userData.name else "Beautiful Human!"
    val theme = getThemeForMbti(userData.mbtiType)

    var currentQuote by remember(userData.mbtiType) {
        mutableStateOf((mbtiQuotes[userData.mbtiType]?.random() ?: generalQuotes.random()))
    }

    var showStaticHeader by remember { mutableStateOf(false) }
    var showDynamicContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        showStaticHeader = true
        delay(400)
        showDynamicContent = true
    }

    val headerAlpha by animateFloatAsState(targetValue = if (showStaticHeader) 1f else 0f, animationSpec = tween(800), label = "h_alpha")
    val contentAlpha by animateFloatAsState(targetValue = if (showDynamicContent) 1f else 0f, animationSpec = tween(1000, 200), label = "c_alpha")
    val contentOffsetY by animateDpAsState(targetValue = if (showDynamicContent) 0.dp else 20.dp, animationSpec = tween(1000, 200), label = "c_offset")
    val infiniteTransition = rememberInfiniteTransition(label = "ken_burns_beranda")
    val scale by infiniteTransition.animateFloat(1f, 1.1f, infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Reverse), "scale_beranda")

    // [KEMBALI KE STABIL] Box dengan Image, FloatingParticles, dan Gradient
    Box(modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = theme.background),
            contentDescription = "Latar Belakang",
            modifier = Modifier.fillMaxSize().scale(scale),
            contentScale = ContentScale.Crop
        )

        // Ini memanggil FloatingParticles dari CommonUI.kt
        FloatingParticles(particleColor = theme.particleColor)

        Box(modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color.Black.copy(0.4f), Color.Transparent, Color.Black.copy(0.7f)))
        ))

        // Tampilkan Kutipan
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 220.dp, start = 48.dp, end = 48.dp)
                .graphicsLayer { alpha = contentAlpha; translationY = contentOffsetY.toPx() }
                .pointerInput(Unit) { detectTapGestures(onTap = { currentQuote = (mbtiQuotes[userData.mbtiType]?.random() ?: generalQuotes.random()) }) },
            contentAlignment = Alignment.Center
        ) {
            Crossfade(currentQuote, animationSpec = tween(500), label = "quote_crossfade") { quoteText -> Text("\"$quoteText\"", style = BerandaGayaKutipan) }
        }

        // Tampilkan Header (Nama & Foto Profil)
        Row(
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .alpha(headerAlpha),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Hi, $displayName", style = BerandaGayaSapaan)
            AsyncImage(
                model = userData.imageUri?.let { Uri.parse(it) } ?: R.drawable.sidikjari,
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
        enter = slideInVertically(animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow), initialOffsetY = { it }) + fadeIn(tween(1000)),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.padding(bottom = 120.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.1f), Color.White.copy(alpha = 0.05f), Color.Transparent)))
                    .border(width = 1.dp, brush = Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)), shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            )
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

@Composable
fun MbtiConstellation(
    mbtiType: String,
    onGemstoneClick: (Char, String) -> Unit,
    onRetakeTest: () -> Unit
) {
    val theme = getThemeForMbti(mbtiType)
    val dimensions = listOf(mbtiType[0] to "Mind", mbtiType[1] to "Energy", mbtiType[2] to "Nature", mbtiType[3] to "Tactics")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(text = mbtiType, style = MbtiConstellationStyle, color = theme.particleColor)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            dimensions.forEach { (char, explanation) ->
                Gemstone(label = char, colors = theme.gemColors, onClick = { onGemstoneClick(char, explanation) })
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Retake Test",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onRetakeTest)
        )
    }
}


@Composable
private fun TakeTestPrompt(onTakeTestClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("What's your type?", style = MaterialTheme.typography.titleLarge, color = Color.White)
        Spacer(Modifier.height(8.dp))
        Text("Take our test to discover your personality type and get personalized insights.", textAlign = TextAlign.Center, color = Color.White.copy(alpha = 0.8f))
        Spacer(Modifier.height(24.dp))
        Button(onClick = onTakeTestClick, shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
            Text("Mulai Tes", color = Color.Black)
        }
    }
}

