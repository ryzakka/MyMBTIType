// File: app/src/main/java/com/dhirekhaf/mytype/MeScreen.kt

package com.dhirekhaf.mytype

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dhirekhaf.mytype.data.UserData
import com.dhirekhaf.mytype.data.UserDataRepository
import com.google.accompanist.flowlayout.FlowRow

// --- GAYA & KONSTANTA ---
private val MeGayaNamaProfil = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
private val MeGayaTipeProfil = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal, color = Color.White.copy(alpha = 0.8f))
private val MeGayaJudulKartu = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
private val MeGayaIsiKartu = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xff3f414e))

// --- SCREEN UTAMA ---
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
    var editEmail by remember(userData.email, isEditMode) { mutableStateOf(userData.email) }
    var editBio by remember(userData.bio, isEditMode) { mutableStateOf(userData.bio) }
    var editHobbies by remember { mutableStateOf<List<String>>(emptyList()) }
    var editImageUri by remember(userData.imageUri, isEditMode) { mutableStateOf(userData.imageUri) }

    LaunchedEffect(isEditMode) {
        if (isEditMode) {
            editHobbies = userData.hobbies.toList()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(it, flag)
                editImageUri = it
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F7))
    ) {
        if (isEditMode) {
            EditProfileScreen(
                name = editName, email = editEmail, bio = editBio, hobbies = editHobbies, imageUri = editImageUri,
                onNameChange = { editName = it },
                onEmailChange = { editEmail = it },
                onBioChange = { editBio = it },
                onHobbyToggle = { hobby ->
                    val currentList = editHobbies.toMutableList()
                    if (currentList.contains(hobby)) currentList.remove(hobby) else currentList.add(hobby)
                    editHobbies = currentList
                },
                onImageClick = { imagePickerLauncher.launch("image/*") },
                onSave = {
                    viewModel.saveUserProfile(editName, editEmail, editBio, editHobbies, editImageUri)
                    if (startInEditMode) navController.navigate("beranda") { popUpTo(navController.graph.id) { inclusive = true } } else isEditMode = false
                },
                onCancel = {
                    if (startInEditMode) navController.navigate("welcome") { popUpTo(navController.graph.id) { inclusive = true } } else isEditMode = false
                }
            )
        } else {
            UserProfileDashboard(
                userData = userData,
                onSettingsClick = { navController.navigate("settings") },
                onEditClick = { isEditMode = true },
                onNavigateToDetail = { typeId, themeColorHex -> navController.navigate("personality_detail/$typeId/$themeColorHex") },
                onTakeTestClick = { navController.navigate("personality_test") }
            )
        }

        AnimatedVisibility(visible = !isEditMode, modifier = Modifier.align(Alignment.BottomCenter)) {
            ModernBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
        }
    }
}

// --- TAMPILAN PROFIL PENGGUNA ---
@Composable
fun UserProfileDashboard(
    userData: UserData,
    onSettingsClick: () -> Unit,
    onEditClick: () -> Unit,
    onNavigateToDetail: (typeId: String, themeColorHex: String) -> Unit,
    onTakeTestClick: () -> Unit
) {
    val personalityData = personalityGroups.flatMap { it.types }.find { it.name == userData.mbtiType }
    val groupData = personalityGroups.find { it.types.contains(personalityData) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(
            userData = userData,
            onSettingsClick = onSettingsClick,
            onEditClick = onEditClick,
            backgroundImageRes = groupData?.backgroundImageRes ?: R.drawable.latarponihijau
        )

        if (userData.mbtiType.isNotEmpty() && personalityData != null && groupData != null) {
            ResultDashboard(
                userData = userData,
                personalityData = personalityData,
                groupData = groupData,
                onNavigateToDetail = onNavigateToDetail
            )
        } else {
            NoTestResultCard(onTakeTestClick = onTakeTestClick)
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

// --- KONTEN HASIL TES ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResultDashboard(
    userData: UserData,
    personalityData: PersonalityType,
    groupData: PersonalityGroupData,
    onNavigateToDetail: (typeId: String, themeColorHex: String) -> Unit
) {
    val theme = getThemeForMbtiGroup(groupData.groupTitle)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DimensionChart(scores = userData.dimensionScores, theme = theme, mbtiType = userData.mbtiType)

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InfoCard(label = "Title", value = personalityData.title, modifier = Modifier.weight(1f))
            InfoCard(label = "Group", value = groupData.groupTitle, modifier = Modifier.weight(1f))
        }

        // Tampilkan bagian hobi jika tidak kosong
        if (userData.hobbies.isNotEmpty()) {
            HobbiesSection(hobbies = userData.hobbies)
        }

        DetailNavCard(
            title = "Learn More About ${userData.mbtiType}",
            subtitle = "See the full analysis of The ${personalityData.title}.",
            theme = theme,
            onClick = {
                val colorHex = String.format("%08X", theme.primaryColor.toArgb())
                onNavigateToDetail(userData.mbtiType, colorHex)
            }
        )
    }
}

// --- HEADER PROFIL ---
@Composable
private fun ProfileHeader(
    userData: UserData,
    onSettingsClick: () -> Unit,
    onEditClick: () -> Unit,
    @DrawableRes backgroundImageRes: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = painterResource(id = backgroundImageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Brush.verticalGradient(listOf(Color.Black.copy(0.5f), Color.Transparent)))
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onEditClick, modifier = Modifier.background(Color.Black.copy(0.3f), CircleShape)) {
                Icon(Icons.Default.Edit, "Edit Profile", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = onSettingsClick, modifier = Modifier.background(Color.Black.copy(0.3f), CircleShape)) {
                Icon(Icons.Default.Settings, "Settings", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = userData.imageUri ?: R.drawable.sidikjari,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White.copy(alpha = 0.9f), CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (userData.name.isNotBlank()) userData.name else "Anonymous",
                style = MeGayaNamaProfil,
                textAlign = TextAlign.Center
            )
            if (userData.mbtiType.isNotEmpty()) {
                Text(
                    text = userData.mbtiType,
                    style = MeGayaTipeProfil,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// --- BAGAN SKOR DIMENSI ---
@Composable
fun DimensionChart(scores: Map<Char, Int>, theme: GroupTheme, mbtiType: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("Your Dimensional Score", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            DimensionBar("Mind", 'E', 'I', scores, theme, mbtiType)
            DimensionBar("Energy", 'S', 'N', scores, theme, mbtiType)
            DimensionBar("Nature", 'T', 'F', scores, theme, mbtiType)
            DimensionBar("Tactics", 'J', 'P', scores, theme, mbtiType)
        }
    }
}

// --- DIMENSION BAR ---
@Composable
private fun DimensionBar(
    title: String,
    leftTrait: Char,
    rightTrait: Char,
    scores: Map<Char, Int>,
    theme: GroupTheme,
    mbtiType: String
) {
    val leftScore = scores.getOrDefault(leftTrait, 0)
    val rightScore = scores.getOrDefault(rightTrait, 0)
    val totalScore = (leftScore + rightScore).coerceAtLeast(1)
    val leftPercentage = leftScore.toFloat() / totalScore
    val rightPercentage = 1f - leftPercentage

    var animationPlayed by remember { mutableStateOf(false) }
    val leftProgress by animateFloatAsState(
        targetValue = if (animationPlayed) leftPercentage else 0f,
        animationSpec = tween(1000, 300, easing = EaseOutCubic), label = "left_prog"
    )
    val rightProgress by animateFloatAsState(
        targetValue = if (animationPlayed) rightPercentage else 0f,
        animationSpec = tween(1000, 300, easing = EaseOutCubic), label = "right_prog"
    )
    LaunchedEffect(Unit) { animationPlayed = true }

    val primaryColor = theme.primaryColor
    val secondaryColor = theme.secondaryColor
    val dominantLeft = mbtiType.contains(leftTrait)

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(leftTrait.toString(), fontWeight = if (dominantLeft) FontWeight.Bold else FontWeight.Normal, color = if (dominantLeft) primaryColor else Color.Gray)
            Text(title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelSmall)
            Text(rightTrait.toString(), fontWeight = if (!dominantLeft) FontWeight.Bold else FontWeight.Normal, color = if (!dominantLeft) primaryColor else Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(CircleShape)
                .background(primaryColor.copy(alpha = 0.1f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(leftProgress.coerceAtLeast(0.001f))
                    .background(primaryColor, CircleShape),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (leftProgress > 0.15f) {
                    Text(text = "${(leftProgress * 100).toInt()}%", color = secondaryColor, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(rightProgress.coerceAtLeast(0.001f)),
                contentAlignment = Alignment.CenterStart
            ) {
                if (rightProgress > 0.15f) {
                    Text(text = "${(rightProgress * 100).toInt()}%", color = primaryColor, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp))
                }
            }
        }
    }
}

// --- [COMPOSABLE BARU] UNTUK MENAMPILKAN HOBI ---
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HobbiesSection(hobbies: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("My Interests", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                hobbies.forEach { hobby ->
                    // --- [PERBAIKAN] ---
                    // Menggunakan SuggestionChip dan BorderStroke secara langsung
                    SuggestionChip(
                        onClick = { /* Tidak ada aksi */ },
                        label = { Text(hobby) },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    )
                }
            }
        }
    }
}

// --- KARTU INFO KECIL ---
@Composable
private fun InfoCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, style = MeGayaJudulKartu)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MeGayaIsiKartu, maxLines = 1)
        }
    }
}

// --- KARTU NAVIGASI DETAIL ---
@Composable
private fun DetailNavCard(
    title: String,
    subtitle: String,
    theme: GroupTheme,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.primaryColor),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.MenuBook,
                contentDescription = null,
                tint = theme.secondaryColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, color = theme.secondaryColor)
                Text(text = subtitle, fontSize = 13.sp, color = theme.secondaryColor.copy(alpha = 0.8f))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = theme.secondaryColor)
        }
    }
}

// --- KARTU JIKA BELUM TES ---
@Composable
fun NoTestResultCard(onTakeTestClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Default.Ballot, null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(16.dp))
            Text("Find Your Type", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("You haven't taken the personality test yet. Discover your type to unlock personalized insights.", textAlign = TextAlign.Center, color = Color.Gray)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onTakeTestClick) { Text("Take the Test Now") }
        }
    }
}

// --- FORMULIR EDIT PROFIL ---
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditProfileScreen(name: String, email: String, bio: String, hobbies: List<String>, imageUri: Uri?, onNameChange: (String) -> Unit, onEmailChange: (String) -> Unit, onBioChange: (String) -> Unit, onHobbyToggle: (String) -> Unit, onImageClick: () -> Unit, onSave: () -> Unit, onCancel: () -> Unit) {
    val allHobbies = listOf("Reading", "Gaming", "Music", "Movies", "Sports", "Cooking", "Traveling", "Art")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onCancel) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Cancel") } },
                actions = { TextButton(onClick = onSave) { Text("Save", fontWeight = FontWeight.Bold, fontSize = 16.sp) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF2F3F7)
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(model = imageUri ?: R.drawable.sidikjari, contentDescription = "Profile Picture", modifier = Modifier.size(120.dp).clip(CircleShape).background(Color.LightGray).clickable(onClick = onImageClick), contentScale = ContentScale.Crop, placeholder = painterResource(id = R.drawable.sidikjari))
                Icon(Icons.Default.Edit, "Edit Image", tint = Color.White, modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary).padding(8.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = bio, onValueChange = onBioChange, label = { Text("Short Bio") }, modifier = Modifier.fillMaxWidth().height(120.dp), maxLines = 4)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("My Interests", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp, modifier = Modifier.fillMaxWidth()) {
                        allHobbies.forEach { hobby ->
                            val isSelected = hobbies.contains(hobby)
                            FilterChip(selected = isSelected, onClick = { onHobbyToggle(hobby) }, label = { Text(hobby) }, leadingIcon = if (isSelected) { { Icon(Icons.Default.Check, "Selected", modifier = Modifier.size(FilterChipDefaults.IconSize)) } } else { null })
                        }
                    }
                }
            }
        }
    }
}
