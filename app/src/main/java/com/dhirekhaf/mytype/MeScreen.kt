// File: app/src/main/java/com/dhirekhaf/mytype/MeScreen.kt

package com.dhirekhaf.mytype

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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

// Gaya dan konstanta lainnya tetap sama
private val MeGayaNamaProfil = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
private val MeGayaTipeProfil = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal, color = Color.White.copy(alpha = 0.8f))
private val MeGayaJudulKartu = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
private val MeGayaIsiKartu = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xff3f414e))

val mbtiBackgrounds = mapOf(
    "INTJ" to R.drawable.latarbodyungu, "INTP" to R.drawable.latarbodyungu,
    "ENTJ" to R.drawable.latarbodyungu, "ENTP" to R.drawable.latarbodyungu,
    "INFJ" to R.drawable.latarbodyhijau, "INFP" to R.drawable.latarbodyhijau,
    "ENFJ" to R.drawable.latarbodyhijau, "ENFP" to R.drawable.latarbodyhijau,
    "ISTJ" to R.drawable.latarbodybiru, "ISFJ" to R.drawable.latarbodybiru,
    "ESTJ" to R.drawable.latarbodybiru, "ESFJ" to R.drawable.latarbodybiru,
    "ISTP" to R.drawable.latarbodykuning, "ISFP" to R.drawable.latarbodykuning,
    "ESTP" to R.drawable.latarbodykuning, "ESFP" to R.drawable.latarbodykuning
)

@Composable
fun MeScreen(
    navController: NavController,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    startInEditMode: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))

    val userData by viewModel.userData.collectAsState()

    var isEditMode by remember { mutableStateOf(startInEditMode) }
    var editName by remember(userData.name, isEditMode) { mutableStateOf(userData.name) }
    var editImageUri by remember(userData.imageUri, isEditMode) { mutableStateOf(userData.imageUri) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)
                editImageUri = uri
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F7))
    ) {
        if (isEditMode) {
            EditProfileForm(
                name = editName,
                imageUri = editImageUri,
                onNameChange = { editName = it },
                onImageClick = { imagePickerLauncher.launch("image/*") },
                onSave = {
                    viewModel.saveNameAndImage(editName, editImageUri)
                    if (startInEditMode) {
                        navController.popBackStack()
                    } else {
                        isEditMode = false
                    }
                },
                onCancel = {
                    if (startInEditMode) {
                        navController.popBackStack()
                    } else {
                        isEditMode = false
                    }
                }
            )
        } else {
            UserProfileDashboard(
                userData = userData,
                onSettingsClick = { navController.navigate("settings") },
                onNavigateToDetail = { typeId, themeColorHex ->
                    navController.navigate("personality_detail/$typeId/$themeColorHex") },
                onTakeTestClick = { navController.navigate("personality_test") }
            )
        }

        if (!isEditMode) {
            ModernBottomNavBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

// Kode BARU yang sudah diperbaiki di MeScreen.kt
@Composable
fun UserProfileDashboard(
    userData: UserData,
    onSettingsClick: () -> Unit,
    // [PERBAIKAN 1] Ubah parameter agar menerima DUA argumen
    onNavigateToDetail: (typeId: String, themeColorHex: String) -> Unit,
    onTakeTestClick: () -> Unit
) {
    val backgroundRes = mbtiBackgrounds[userData.mbtiType] ?: R.drawable.latarponihijau
    val personalityData = personalityGroups.flatMap { it.types }.find { it.name == userData.mbtiType }
    val groupData = personalityGroups.find { it.types.contains(personalityData) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ... (Box header profil tidak perlu diubah) ...
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = backgroundRes), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(Color.Black.copy(0.3f), Color.Transparent, Color.Black.copy(0.2f)), startY = 0f, endY = 800f)))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model = userData.imageUri ?: R.drawable.sidikjari, contentDescription = "Profile Picture", contentScale = ContentScale.Crop,
                    modifier = Modifier.size(110.dp).clip(CircleShape).border(4.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = userData.name.ifEmpty { "Your Name" }, style = MeGayaNamaProfil, textAlign = TextAlign.Center)
                if (userData.mbtiType.isNotEmpty()) {
                    Text(text = userData.mbtiType, style = MeGayaTipeProfil, textAlign = TextAlign.Center)
                }
            }
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).background(Color.Black.copy(alpha = 0.4f), CircleShape)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }


        if (userData.mbtiType.isNotEmpty() && personalityData != null && groupData != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DimensionChart(scores = userData.dimensionScores, mbtiType = userData.mbtiType)

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    InfoCard(label = "Title", value = personalityData.title, modifier = Modifier.weight(1f))
                    InfoCard(label = "Group", value = groupData.groupTitle, modifier = Modifier.weight(1f))
                }
                DetailNavCard(
                    title = "Learn More About ${userData.mbtiType}",
                    subtitle = "See the full analysis of The ${personalityData.title}.",
                    // [PERBAIKAN 2] Panggil navigasi dengan DUA argumen
                    onClick = {
                        // Dapatkan tema warna berdasarkan grup
                        val theme = getThemeForMbtiGroup(groupData.groupTitle)
                        // Ubah warna menjadi Hex String
                        val colorHex = String.format("%08X", theme.primaryColor.toArgb())
                        // Panggil navigasi dengan kedua argumen
                        onNavigateToDetail(userData.mbtiType, colorHex)
                    }
                )
            }
        } else {
            TakeTestPrompt(onTakeTestClick = onTakeTestClick)
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun DimensionChart(scores: Map<Char, Int>, mbtiType: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Your Personality Composition", style = MeGayaIsiKartu)
            Spacer(modifier = Modifier.height(4.dp))

            DimensionBar("Mind", "I", "E", scores, mbtiType)
            DimensionBar("Energy", "N", "S", scores, mbtiType)
            DimensionBar("Nature", "F", "T", scores, mbtiType)
            DimensionBar("Tactics", "P", "J", scores, mbtiType)
        }
    }
}

// File: app/src/main/java/com/dhirekhaf/mytype/MeScreen.kt

// ... (kode lain di atasnya biarkan sama)

@Composable
private fun DimensionBar(
    title: String,
    leftTrait: String,
    rightTrait: String,
    scores: Map<Char, Int>,
    mbtiType: String
) {
    val leftScore = scores.getOrDefault(leftTrait.first(), 0)
    val rightScore = scores.getOrDefault(rightTrait.first(), 0)
    val totalScore = (leftScore + rightScore).coerceAtLeast(1)

    val leftPercentage = leftScore.toFloat() / totalScore
    val rightPercentage = rightScore.toFloat() / totalScore

    var animationPlayed by remember { mutableStateOf(false) }
    val leftProgress by animateFloatAsState(
        targetValue = if (animationPlayed) leftPercentage else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 300),
        label = "left_prog_${leftTrait}"
    )
    val rightProgress by animateFloatAsState(
        targetValue = if (animationPlayed) rightPercentage else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 300),
        label = "right_prog_${rightTrait}"
    )
    LaunchedEffect(Unit) { animationPlayed = true }

    val theme = getThemeForMbti(mbtiType)
    val primaryColor = theme.gemColors.firstOrNull() ?: MaterialTheme.colorScheme.primary

    val dominantLeft = mbtiType.contains(leftTrait.first())
    val dominantRight = mbtiType.contains(rightTrait.first())

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(leftTrait, fontWeight = if (dominantLeft) FontWeight.Bold else FontWeight.Normal, color = if (dominantLeft) primaryColor else Color.Gray)
            Text(title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelMedium)
            Text(rightTrait, fontWeight = if (dominantRight) FontWeight.Bold else FontWeight.Normal, color = if (dominantRight) primaryColor else Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // --- PERUBAHAN UTAMA DI SINI ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp) // Sedikit lebih tinggi untuk memberi ruang teks
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray.copy(alpha = 0.1f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Box untuk Bar Kiri (dengan teks persentase)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(leftProgress.coerceAtLeast(0.001f))
                    .background(primaryColor),
                contentAlignment = Alignment.CenterEnd // Teks di ujung kanan bar kiri
            ) {
                // Tampilkan teks hanya jika bar cukup lebar
                if (leftProgress > 0.15f) {
                    Text(
                        text = "${(leftProgress * 100).toInt()}%",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
            // Box untuk Bar Kanan (dengan teks persentase)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(rightProgress.coerceAtLeast(0.001f))
                    .background(primaryColor.copy(alpha = 0.5f)),
                contentAlignment = Alignment.CenterStart // Teks di ujung kiri bar kanan
            ) {
                // Tampilkan teks hanya jika bar cukup lebar
                if (rightProgress > 0.15f) {
                    Text(
                        text = "${(rightProgress * 100).toInt()}%",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

// ... (sisa kode MeScreen.kt biarkan seperti semula)

@Composable
private fun InfoCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(0.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, style = MeGayaJudulKartu)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MeGayaIsiKartu, maxLines = 1)
        }
    }
}

@Composable
private fun DetailNavCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(text = subtitle, fontSize = 13.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@Composable
fun TakeTestPrompt(onTakeTestClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp).clickable(onClick = onTakeTestClick), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Box(modifier = Modifier.background(brush = Brush.linearGradient(colors = listOf(Color(0xFF8E97FD), Color(0xFF735283))))) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Discover Your Personality", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Take the test and find your type.", style = TextStyle(fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f)))
                }
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileForm(
    name: String,
    imageUri: Uri?,
    onNameChange: (String) -> Unit,
    onImageClick: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text("Edit Profile") },
            navigationIcon = {
                IconButton(onClick = onCancel) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Cancel")
                }
            },
            actions = {
                TextButton(onClick = onSave) {
                    Text("Save", fontWeight = FontWeight.Bold)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = imageUri ?: R.drawable.sidikjari,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onImageClick)
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Image",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Your Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
