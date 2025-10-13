package com.dhirekhaf.mytype

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.dhirekhaf.mytype.data.UserData
import com.dhirekhaf.mytype.data.UserDataRepository
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SharePreviewScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by viewModel.userData.collectAsState()

    val personalityInfo = personalityGroupsForList.flatMap { it.types }.find { it.typeName == userData.mbtiType }
    val groupData = personalityGroupsForList.find { group -> group.types.any { it.typeName == userData.mbtiType } }
    val theme = getThemeForMbtiGroup(groupData?.groupTitle ?: "Default")
    val personalityTheme = getThemeForMbti(userData.mbtiType)

    val bingoTraits = personalityBingoMap[userData.mbtiType] ?: emptyList()
    val selectedBingoItems = remember { mutableStateMapOf<BingoTrait, Boolean>() }
    val bingoSelectionCount by remember { derivedStateOf { selectedBingoItems.count { it.value } } }

    val captureController = rememberCaptureController()
    val coroutineScope = rememberCoroutineScope()
    var isSharing by remember { mutableStateOf(false) }
    val tabs = listOf("Kartu Bingo", "Kartu Aura")
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Box(modifier = Modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition("ken_burns_share")
        val scale by infiniteTransition.animateFloat(1.0f, 1.1f, infiniteRepeatable(tween(20000), RepeatMode.Reverse), "scale_share")
        Image(
            painter = painterResource(personalityTheme.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().scale(scale)
        )
        FloatingParticles(particleColor = personalityTheme.particleColor)
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Black.copy(0.2f), Color.Transparent, Color.Black.copy(0.5f)))))

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pilih Kartu untuk Dibagikan", fontWeight = FontWeight.Bold, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Pratinjau Kartu", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
                Spacer(Modifier.height(8.dp))

                Capturable(
                    controller = captureController,
                    onCaptured = { imageBitmap, error ->
                        coroutineScope.launch {
                            handleCapture(context, imageBitmap?.asAndroidBitmap(), error) { isSharing = false }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    if (personalityInfo != null) {
                        if (pagerState.currentPage == 0) {
                            BingoShareCard(userData, personalityInfo, bingoTraits, theme, selectedBingoItems)
                        } else {
                            AuraShareCard(userData, personalityInfo, theme)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = theme.primaryColor,
                            height = 3.dp
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            text = { Text(title, fontWeight = FontWeight.Bold) }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                ) { page ->
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        when (page) {
                            0 -> BingoControls(
                                bingoTraits = bingoTraits,
                                selectedItems = selectedBingoItems,
                                onItemClick = { trait -> selectedBingoItems[trait] = !(selectedBingoItems[trait] ?: false) },
                                theme = theme
                            )
                            1 -> AuraControls()
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        isSharing = true
                        captureController.capture()
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isSharing && (pagerState.currentPage == 1 || (pagerState.currentPage == 0 && bingoSelectionCount > 0)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    if (isSharing) {
                        CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Filled.Share, "Bagikan", tint = Color.Black, modifier = Modifier.padding(end = 8.dp))
                        val buttonText = if (pagerState.currentPage == 0) "Bagikan Kartu Bingo ($bingoSelectionCount terpilih)" else "Bagikan Kartu Aura"
                        Text(buttonText, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

private suspend fun handleCapture(
    context: Context,
    bitmap: Bitmap?,
    error: Throwable?,
    onFinish: () -> Unit
) {
    if (bitmap != null) {
        withContext(Dispatchers.IO) {
            shareBitmap(context, bitmap)
        }
    } else {
        val errorMessage = error?.message ?: "Gagal mengambil gambar. Coba lagi."
        withContext(Dispatchers.Main) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
    withContext(Dispatchers.Main) {
        onFinish()
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

@Composable
private fun BingoControls(
    bingoTraits: List<BingoTrait>,
    selectedItems: Map<BingoTrait, Boolean>,
    onItemClick: (BingoTrait) -> Unit,
    theme: GroupTheme
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Pilih kotak yang paling 'kamu banget'!", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, color = Color.White)
        InteractiveBingoGrid(
            traits = bingoTraits,
            theme = theme,
            selectedItems = selectedItems,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun AuraControls() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Text("Aura ini adalah representasi unik dari skormu. Siap untuk dibagikan!", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, color = Color.White)
    }
}

@Composable
fun AuraShareCard(
    userData: UserData,
    personalityInfo: PersonalityInfo,
    theme: GroupTheme
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .border(1.dp, theme.primaryColor.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BingoHeader(personalityInfo = personalityInfo, userData = userData, textColor = Color.White)
            Spacer(Modifier.weight(1f))
            PersonalityAura(scores = userData.dimensionScores, theme = theme)
            Spacer(Modifier.weight(1f))
            Text("Lihat Aura Kepribadianku di MyType App", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
    }
}

@Composable
fun BingoShareCard(
    userData: UserData,
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
            StaticBingoGrid(traits = traits, theme = theme, selectedItems = selectedItems)
            Spacer(modifier = Modifier.weight(1f))
            Text("Tes kepribadian-mu di", color = textColor.copy(alpha = 0.8f), fontSize = 12.sp)
            Text("MyType App", color = textColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}


@Composable
fun BingoHeader(personalityInfo: PersonalityInfo, userData: UserData, textColor: Color) {
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
            Text(text = personalityInfo.typeName, color = textColor, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 38.sp)
            Text(text = personalityInfo.title, color = textColor.copy(alpha = 0.9f), fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black.copy(alpha = 0.2f))
                    .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(text = userData.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun InteractiveBingoGrid(
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
fun RowScope.InteractiveBingoCell(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    theme: GroupTheme,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(if (isSelected) theme.primaryColor else Color.White.copy(alpha = 0.6f), label = "bingoCellBg")
    val textColor by animateColorAsState(if (isSelected) Color.White else Color.Black.copy(alpha = 0.8f), label = "bingoCellText")
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
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(4.dp)) {
            Text(
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun StaticBingoGrid(
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
fun RowScope.StaticBingoCell(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    theme: GroupTheme
) {
    val color = if (isSelected) theme.secondaryColor.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.1f)
    val textColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.9f)
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
