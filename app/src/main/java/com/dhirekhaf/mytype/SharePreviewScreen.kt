// File: app/src/main/java/com/dhirekhaf/mytype/SharePreviewScreen.kt
// [KODE FINAL v10.0 - GRID 3x3 SIMETRIS & STABIL]

package com.dhirekhaf.mytype

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
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
    val bingoTraits = personalityBingoMap[userData.mbtiType]

    val captureController = rememberCaptureController()
    val coroutineScope = rememberCoroutineScope()
    var isSharing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preview Kartu Bingo", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    theme.primaryColor.copy(alpha = 0.1f),
                    Color(0xFFF0F0F0)
                )
            )
        )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(9f / 19f) // Dibuat lebih panjang agar grid muat
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    if (personalityInfo != null && bingoTraits != null) {
                        BingoShareCard(
                            userData = userData,
                            personalityInfo = personalityInfo,
                            traits = bingoTraits,
                            theme = theme
                        )
                    } else {
                        Text("Data tidak lengkap untuk membuat kartu.")
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Text("Bagikan sebagai Story atau Postingan!", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Button(
                    onClick = {
                        isSharing = true
                        captureController.capture()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isSharing
                ) {
                    if (isSharing) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Filled.Share, "Bagikan", modifier = Modifier.padding(end = 8.dp))
                        Text("Bagikan Sekarang", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun BingoShareCard(
    userData: com.dhirekhaf.mytype.data.UserData,
    personalityInfo: PersonalityInfo,
    traits: List<BingoTrait>,
    theme: GroupTheme
) {
    val backgroundColor = theme.primaryColor.copy(alpha = 0.9f)
    val textColor = Color.White
    val gridColor = Color.White.copy(alpha = 0.4f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        theme.primaryColor,
                        backgroundColor
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BingoHeader(personalityInfo = personalityInfo, userData = userData, textColor = textColor)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Lingkari Yang Mencerminkan Dirimu!", color = textColor, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        BingoGrid(traits = traits, gridColor = gridColor, textColor = textColor)
        Spacer(modifier = Modifier.weight(1f))
        Text("Tes kepribadian-mu di", color = textColor.copy(alpha = 0.8f), fontSize = 14.sp)
        Text("MyType App", color = textColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
private fun BingoHeader(personalityInfo: PersonalityInfo, userData: com.dhirekhaf.mytype.data.UserData, textColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = personalityInfo.cardImageRes),
            contentDescription = personalityInfo.typeName,
            modifier = Modifier.size(80.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(personalityInfo.typeName, color = textColor, fontSize = 48.sp, fontWeight = FontWeight.ExtraBold)
            Text(personalityInfo.title, color = textColor.copy(alpha = 0.9f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(userData.name, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- [REVISI TOTAL] Grid 3x3 yang Dijamin Simetris ---
@Composable
private fun BingoGrid(traits: List<BingoTrait>, gridColor: Color, textColor: Color) {
    // 1. Ambil 9 item pertama, atau kurang jika tidak cukup.
    val gridItems = traits.take(9).toMutableList()

    // 2. Jika item kurang dari 9, isi sisanya dengan item kosong agar simetris.
    while (gridItems.size < 9) {
        gridItems.add(BingoTrait("", 1)) // Item kosong
    }

    // 3. Buat grid menggunakan Column dan Row, dengan chunked(3) yang simpel.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Buat area grid menjadi persegi sempurna
            .border(1.dp, gridColor) // Satu border untuk luar
    ) {
        // 4. `chunked(3)` akan secara otomatis membuat 3 baris, masing-masing berisi 3 item.
        gridItems.chunked(3).forEach { rowItems ->
            Row(Modifier.weight(1f)) {
                rowItems.forEach { trait ->
                    // 5. Setiap sel mendapat weight 1f, membagi lebar secara merata.
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(0.5.dp, gridColor)
                    ) {
                        BingoCell(text = trait.text, textColor = textColor)
                    }
                }
            }
        }
    }
}

// [MODIFIKASI KECIL] - Modifier tidak lagi dibutuhkan sebagai parameter
@Composable
private fun BingoCell(text: String, textColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp), // Padding di dalam border
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            fontWeight = if (text.all { it.isUpperCase() } && text.length > 3) FontWeight.Bold else FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 14.sp
        )
    }
}


// --- FUNGSI HELPER (Tidak Berubah) ---
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
        val chooser = Intent.createChooser(intent, "Bagikan via")
        context.startActivity(chooser)
    }
}
