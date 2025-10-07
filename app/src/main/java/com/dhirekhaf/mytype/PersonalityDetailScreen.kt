// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityDetailScreen.kt

package com.dhirekhaf.mytype

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.PersonalityDetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    typeId: String,
    navController: NavController,
    themeColorHex: String // <-- [PERUBAHAN] Terima warna sebagai argumen
) {
    // Ubah Hex String kembali menjadi objek Color
    val themeColor = Color(android.graphics.Color.parseColor("#$themeColorHex"))

    val details = personalityDetailsMap[typeId]
    val personalityInfo = personalityGroups.flatMap { it.types }.find { it.name == typeId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(details?.typeName ?: "Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )
            )
        },
        containerColor = Color(0xFFF2F3F7)
    ) { paddingValues ->
        if (details != null && personalityInfo != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    DetailTextHeader(details = details, personalityInfo = personalityInfo)
                }
                item {
                    // Kirim warna tema ke Tab
                    DetailTabs(details = details, themeColor = themeColor)
                }
                item {
                    GallerySection(
                        animatedVisibilityScope = animatedVisibilityScope,
                        details = details,
                        personalityInfo = personalityInfo
                    )
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Detail untuk tipe $typeId tidak ditemukan.")
            }
        }
    }
}

// Composable DetailTextHeader tidak berubah
@Composable
fun DetailTextHeader(details: PersonalityDetails, personalityInfo: PersonalityType) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFFBFBFF))
                )
            )
            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = details.typeName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "\"${personalityInfo.title}\"",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = details.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            lineHeight = 22.sp
        )
    }
}

// --- [PERUBAHAN] DetailTabs sekarang menerima dan menggunakan themeColor ---
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailTabs(details: PersonalityDetails, themeColor: Color) { // Terima themeColor
    val tabs = listOf("Kekuatan", "Kelemahan", "Karier", "Hubungan", "Saran")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(top = 16.dp)) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = themeColor, // Gunakan themeColor untuk teks tab
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = themeColor, // Gunakan themeColor untuk indikator
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { page ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(24.dp)
                    .defaultMinSize(minHeight = 300.dp)
            ) {
                // Pilih konten berdasarkan halaman, dan kirimkan warna tema
                when (page) {
                    0 -> InfoList(title = "Kekuatan Utama", items = details.strengths, themeColor = themeColor)
                    1 -> InfoList(title = "Potensi Kelemahan", items = details.weaknesses, themeColor = themeColor)
                    2 -> InfoList(title = "Saran Jenjang Karier", items = details.careerPaths, themeColor = themeColor)
                    3 -> InfoParagraph(title = "Dalam Hubungan", text = details.relationships)
                    4 -> InfoList(title = "Tips Pengembangan Diri", items = details.developmentTips, themeColor = themeColor)
                }
            }
        }
    }
}

// GallerySection tidak perlu diubah, karena sudah sempurna dari langkah sebelumnya
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GallerySection(
    animatedVisibilityScope: AnimatedVisibilityScope,
    details: PersonalityDetails,
    personalityInfo: PersonalityType
) {
    val fullGallery = listOf(personalityInfo.sliceImage) + details.detailImages

    Column(
        modifier = Modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            "Galeri",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        fullGallery.forEachIndexed { index, imageRes ->
            val isMainAnimatedImage = index == 1
            val imageModifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(16.dp))

            val finalModifier = if (isMainAnimatedImage) {
                imageModifier.sharedElement(
                    rememberSharedContentState(key = "image-${personalityInfo.name}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            } else {
                imageModifier
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Gambar Galeri ${details.typeName} #${index + 1}",
                contentScale = ContentScale.FillWidth,
                modifier = finalModifier
            )
        }
    }
}


// --- [PERUBAHAN] InfoList sekarang menggunakan themeColor untuk bullet points ---
@Composable
fun InfoList(title: String, items: List<String>, themeColor: Color) { // Terima themeColor
    Column {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        items.forEach { item ->
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(bottom = 12.dp)) {
                Text(
                    "•",
                    modifier = Modifier.padding(end = 12.dp),
                    fontSize = 16.sp,
                    color = themeColor, // Gunakan themeColor untuk bullet point
                    fontWeight = FontWeight.Bold
                )
                Text(item, style = MaterialTheme.typography.bodyLarge, lineHeight = 24.sp)
            }
        }
    }
}

// InfoParagraph tidak perlu diubah
@Composable
fun InfoParagraph(title: String, text: String) {
    Column {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge, lineHeight = 24.sp, textAlign = TextAlign.Justify)
    }
}
