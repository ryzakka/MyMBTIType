// File: app/src/main/java/com/dhirekhaf/mytype/SharePreviewScreen.kt
// [VERSI FINAL - DENGAN LATAR BELAKANG DINAMIS SESUAI TIPE]

package com.dhirekhaf.mytype

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dhirekhaf.mytype.data.UserDataRepository
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharePreviewScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by viewModel.userData.collectAsState()
    val personalityInfo = personalityGroupsForList.flatMap { it.types }.find { it.typeName == userData.mbtiType }
    val groupData = personalityGroupsForList.find { group -> group.types.any { it.typeName == userData.mbtiType } }
    val theme = getThemeForMbtiGroup(groupData?.groupTitle ?: "Default")
    val bingoTraits = personalityBingoMap[userData.mbtiType] ?: emptyList()

    // Ambil tema visual berdasarkan tipe MBTI pengguna
    val personalityTheme = getThemeForMbti(userData.mbtiType)

    val captureController = rememberCaptureController()
    val coroutineScope = rememberCoroutineScope()
    var isSharing by remember { mutableStateOf(false) }

    val selectedItems = remember { mutableStateMapOf<BingoTrait, Boolean>() }
    val selectionCount by remember { derivedStateOf { selectedItems.count { it.value } } }

    // --- [MODIFIKASI UTAMA] ---
    // Menggunakan Box sebagai root untuk menumpuk latar belakang dan konten
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Latar Belakang Dinamis (Gambar + Partikel + Gradien)
        val infiniteTransition = rememberInfiniteTransition("ken_burns_share")
        val scale by infiniteTransition.animateFloat(1.0f, 1.1f, infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Reverse), "scale_share")

        Image(
            painter = painterResource(personalityTheme.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().scale(scale)
        )
        FloatingParticles(particleColor = personalityTheme.particleColor)
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Black.copy(0.2f), Color.Transparent, Color.Black.copy(0.5f)))))

        // 2. Scaffold dengan background transparan
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Kustomisasi Kartu Bingo", fontWeight = FontWeight.Bold, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent // <-- PENTING: Scaffold dibuat transparan
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kartu ini yang akan dibagikan.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(Modifier.height(8.dp))

                // ... Sisa kode tidak berubah ...
                Capturable(
                    controller = captureController,
                    onCaptured = { imageBitmap, error ->
                        coroutineScope.launch {
                            if (imageBitmap != null) {
                                withContext(Dispatchers.IO) {
                                    val androidBitmap = imageBitmap.asAndroidBitmap()
                                    shareBitmap(context, androidBitmap)
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    android.widget.Toast.makeText(context, "Gagal membuat gambar: ${error?.message}", android.widget.Toast.LENGTH_LONG).show()
                                }
                            }
                            isSharing = false
                        }
                    }
                ) {
                    if (personalityInfo != null) {
                        BingoShareCard(
                            userData = userData,
                            personalityInfo = personalityInfo,
                            traits = bingoTraits,
                            theme = theme,
                            selectedItems = selectedItems
                        )
                    } else {
                        Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                            Text("Data kepribadian tidak ditemukan.")
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(color = Color.White.copy(alpha = 0.2f))
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "Pilih kotak yang paling 'kamu banget'!",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Spacer(Modifier.height(16.dp))
                    InteractiveBingoGrid(
                        traits = bingoTraits,
                        theme = theme,
                        selectedItems = selectedItems,
                        onItemClick = { trait ->
                            selectedItems[trait] = !(selectedItems[trait] ?: false)
                        }
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = {
                            isSharing = true
                            captureController.capture()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isSharing && selectionCount > 0,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        if (isSharing) {
                            CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Filled.Share, "Bagikan", tint = Color.Black, modifier = Modifier.padding(end = 8.dp))
                            Text(
                                "Bagikan Kartu ($selectionCount terpilih)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}


// vvv [MODIFIKASI KECIL PADA HEADER AGAR LEBIH KONTRAST] vvv
@Composable
private fun BingoHeader(personalityInfo: PersonalityInfo, userData: com.dhirekhaf.mytype.data.UserData, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = personalityInfo.cardImageRes),
            contentDescription = personalityInfo.typeName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1.3f)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = personalityInfo.typeName,
                color = textColor,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 38.sp
            )
            Text(
                text = personalityInfo.title,
                color = textColor.copy(alpha = 0.9f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    // Ubah background agar lebih kontras dengan latar gelap
                    .background(Color.Black.copy(alpha = 0.2f))
                    .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = userData.name,
                    color = Color.White, // Teks jadi putih
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}


// ... Sisa Composable (BingoShareCard, Grid, Cells, dll) tidak perlu diubah secara signifikan ...
// Anda bisa menggunakan kode yang sudah ada untuk fungsi-fungsi di bawah ini.
@Composable
private fun BingoShareCard(
    userData: com.dhirekhaf.mytype.data.UserData,
    personalityInfo: PersonalityInfo,
    traits: List<BingoTrait>,
    theme: GroupTheme,
    selectedItems: Map<BingoTrait, Boolean>
) {
    val backgroundColor = theme.primaryColor.copy(alpha = 0.9f)
    val textColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        theme.primaryColor,
                        backgroundColor
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BingoHeader(personalityInfo = personalityInfo, userData = userData, textColor = textColor)
            Spacer(modifier = Modifier.height(20.dp))
            Text("MY PERSONALITY BINGO", color = textColor, fontWeight = FontWeight.Black, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            StaticBingoGrid(
                traits = traits,
                theme = theme,
                selectedItems = selectedItems
            )
            Spacer(modifier = Modifier.weight(1f))
            Text("Tes kepribadian-mu di", color = textColor.copy(alpha = 0.8f), fontSize = 12.sp)
            Text("MyType App", color = textColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
private fun StaticBingoGrid(
    traits: List<BingoTrait>,
    theme: GroupTheme,
    selectedItems: Map<BingoTrait, Boolean>
) {
    val gridItems = traits.take(16)
    Column(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
    ) {
        gridItems.chunked(4).forEach { rowItems ->
            Row(Modifier.weight(1f)) {
                rowItems.forEach { trait ->
                    val isSelected = selectedItems[trait] ?: false
                    StaticBingoCell(
                        modifier = Modifier.weight(1f),
                        text = trait.text,
                        isSelected = isSelected,
                        theme = theme
                    )
                }
            }
        }
    }
}

@Composable
private fun InteractiveBingoGrid(
    traits: List<BingoTrait>,
    theme: GroupTheme,
    selectedItems: Map<BingoTrait, Boolean>,
    onItemClick: (BingoTrait) -> Unit
) {
    val gridItems = traits.take(16)
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        gridItems.chunked(4).forEach { rowItems ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowItems.forEach { trait ->
                    val isSelected = selectedItems[trait] ?: false
                    InteractiveBingoCell(
                        modifier = Modifier.weight(1f),
                        text = trait.text,
                        isSelected = isSelected,
                        theme = theme,
                        onClick = { onItemClick(trait) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.StaticBingoCell(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    theme: GroupTheme
) {
    val color = if (isSelected) theme.secondaryColor.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.1f)
    val textColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.9f) // Ubah agar lebih terbaca

    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(color)
            .border(0.5.dp, Color.White.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 9.sp,
            lineHeight = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(3.dp)
        )
    }
}

@Composable
private fun InteractiveBingoCell(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    theme: GroupTheme,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(if (isSelected) theme.primaryColor else Color.White.copy(alpha = 0.6f), label = "")
    val textColor by animateColorAsState(if (isSelected) Color.White else Color.Black.copy(alpha = 0.8f), label = "")
    val border = if (isSelected) BorderStroke(2.dp, theme.primaryColor) else BorderStroke(1.dp, Color.White.copy(alpha = 0.8f))

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = border,
        elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 2.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

private fun shareBitmap(context: Context, bitmap: Bitmap) {
    val imagePath = File(context.cacheDir, "images")
    var file: File? = null
    try {
        imagePath.mkdirs()
        file = File(imagePath, "shared_image.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    if (file != null) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Bagikan via"))
    }
}

