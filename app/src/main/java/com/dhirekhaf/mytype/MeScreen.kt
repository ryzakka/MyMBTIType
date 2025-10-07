// File: app/src/main/java/com/dhirekhaf/mytype/MeScreen.kt
// [PERBAIKAN FINAL] Menambahkan padding bawah yang signifikan untuk mengangkat konten dasbor.

package com.dhirekhaf.mytype

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dhirekhaf.mytype.data.UserData
import com.dhirekhaf.mytype.data.UserDataRepository
import android.content.Intent as AndroidIntent

@OptIn(ExperimentalMaterial3Api::class)
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

    val groupData = personalityGroupsForList.find { group -> group.types.any { it.typeName == userData.mbtiType } }
    val theme = getThemeForMbtiGroup(groupData?.groupTitle ?: "Default")

    val personalityTheme = getThemeForMbti(userData.mbtiType)

    var isEditMode by remember { mutableStateOf(startInEditMode) }
    var editName by remember(userData.name, isEditMode) { mutableStateOf(userData.name) }
    var editEmail by remember(userData.email, isEditMode) { mutableStateOf(userData.email) }
    var editBio by remember(userData.bio, isEditMode) { mutableStateOf(userData.bio) }
    var editHobbies by remember { mutableStateOf<List<String>>(emptyList()) }
    var editImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(userData, isEditMode) {
        if (isEditMode) {
            editHobbies = userData.hobbies.toList()
            editImageUri = userData.imageUri?.let { Uri.parse(it) }
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val flag = AndroidIntent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(it, flag)
                editImageUri = it
            }
        }
    )

    Box(modifier = modifier.fillMaxSize()) {
        if (isEditMode) {
            EditProfileScreen(
                name = editName, email = editEmail, bio = editBio, hobbies = editHobbies,
                imageUri = editImageUri, theme = theme,
                onNameChange = { editName = it }, onEmailChange = { editEmail = it }, onBioChange = { editBio = it },
                onHobbyToggle = { hobby ->
                    val currentList = editHobbies.toMutableList()
                    if (currentList.contains(hobby)) currentList.remove(hobby) else currentList.add(hobby)
                    editHobbies = currentList
                },
                onImageClick = { imagePickerLauncher.launch("image/*") },
                onSave = {
                    viewModel.saveUserProfile(editName, editEmail, editBio, editHobbies, editImageUri)
                    if (startInEditMode) navController.navigate("beranda") { popUpTo(navController.graph.startDestinationId) { inclusive = true } } else isEditMode = false
                },
                onCancel = {
                    if (startInEditMode) navController.navigate("welcome") { popUpTo(navController.graph.startDestinationId) { inclusive = true } } else isEditMode = false
                }
            )
        } else {
            PersonalizedDashboard(
                userData = userData,
                personalityTheme = personalityTheme,
                onEditClick = { isEditMode = true },
                onShareClick = {
                    if (userData.mbtiType.isNotEmpty()) {
                        navController.navigate("share_preview")
                    } else {
                        android.widget.Toast.makeText(context, "Selesaikan tes untuk bisa berbagi!", android.widget.Toast.LENGTH_SHORT).show()
                    }
                },
                onSettingsClick = { navController.navigate("settings") },
                onNavigateToDetail = { typeId, themeColorHex -> navController.navigate("personality_detail/$typeId/$themeColorHex") },
                onNavigateToRelation = { type1, type2 -> navController.navigate("relationship_detail/$type1/$type2") },
                onTakeTestClick = { navController.navigate("personality_test") }
            )
        }

        val isMeScreen = currentRoute == "me"
        AnimatedVisibility(visible = !isEditMode && isMeScreen, modifier = Modifier.align(Alignment.BottomCenter)) {
            ModernBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
        }
    }
}

@Composable
fun PersonalizedDashboard(
    userData: UserData,
    personalityTheme: PersonalityTheme,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onNavigateToDetail: (typeId: String, themeColorHex: String) -> Unit,
    onNavigateToRelation: (type1: String, type2: String) -> Unit,
    onTakeTestClick: () -> Unit
) {
    val personalityData = personalityGroupsForList.flatMap { it.types }.find { it.typeName == userData.mbtiType }
    val groupData = personalityGroupsForList.find { group -> group.types.any { it.typeName == userData.mbtiType } }

    Box(modifier = Modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition("ken_burns_me")
        val scale by infiniteTransition.animateFloat(1.0f, 1.1f, infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Reverse), "scale_me")

        Image(
            painter = painterResource(personalityTheme.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().scale(scale)
        )
        FloatingParticles(personalityTheme.particleColor)
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Black.copy(0.4f), Color.Transparent, Color.Black.copy(0.7f)))))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PersonalizedHeader(
                userData = userData,
                onEditClick = onEditClick,
                onShareClick = onShareClick,
                onSettingsClick = onSettingsClick
            )

            if (personalityData != null && groupData != null) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ResultDashboard(
                        userData = userData,
                        personalityData = personalityData,
                        groupData = groupData,
                        onNavigateToDetail = onNavigateToDetail,
                        onNavigateToRelation = onNavigateToRelation
                    )
                }
            } else {
                NoTestResultCard(onTakeTestClick = onTakeTestClick)
            }

            // [PERBAIKAN UTAMA] Nilai Spacer dinaikkan menjadi 120.dp
            // Ini akan mendorong semua konten ke atas, memberikan ruang untuk navbar.
            Spacer(modifier = Modifier.height(160.dp))
        }
    }
}

@Composable
private fun PersonalizedHeader(
    userData: UserData,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlassmorphismIconButton(onClick = onShareClick) {
                Icon(Icons.Default.Share, "Share", tint = Color.White)
            }
            GlassmorphismIconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, "Settings", tint = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = userData.imageUri ?: R.drawable.ic_account_circle_24,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color.White.copy(alpha = 0.8f), CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_account_circle_24)
            )

            Text(
                text = userData.name.ifEmpty { "Pengguna MyType" },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            if (userData.bio.isNotBlank()) {
                Text(
                    text = userData.bio,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Button(
                onClick = onEditClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                Icon(Icons.Default.Edit, "Edit Profil", tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Edit Profil", color = Color.White)
            }
        }
    }
}

@Composable
fun GlassmorphismIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.15f))
            .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
