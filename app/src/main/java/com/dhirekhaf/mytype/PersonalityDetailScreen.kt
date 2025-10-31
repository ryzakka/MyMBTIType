// Lokasi: app/src/main/java/com/dhirekhaf/mytype/PersonalityDetailScreen.kt

package com.dhirekhaf.mytype

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.dhirekhaf.mytype.data.UserDataRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.PersonalityDetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    typeId: String,
    navController: NavController,
    themeColorHex: String
) {
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by meViewModel.userData.collectAsState()

    val isFavorite = userData.favoriteTypes.containsKey(typeId)
    val currentLabel = userData.favoriteTypes[typeId] ?: ""
    var showLabelDialog by remember { mutableStateOf(false) }

    val correctedColorHex = if (themeColorHex.length == 6) "FF$themeColorHex" else themeColorHex
    val themeColor = Color(android.graphics.Color.parseColor("#$correctedColorHex"))
    val details = personalityDetailsMap[typeId]
    val personalityInfo = personalityGroupsForList.flatMap { it.types }.find { it.typeName == typeId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(details?.typeName ?: "Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isFavorite) {
                            meViewModel.removeFavorite(typeId)
                        } else {
                            showLabelDialog = true
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = "Favorit",
                            tint = if (isFavorite) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
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
                item { DetailTextHeader(details = details, personalityInfo = personalityInfo) }
                item {
                    DetailTabs(
                        details = details,
                        bingoTraits = personalityBingoMap[typeId] ?: emptyList(),
                        themeColor = themeColor,
                        navController = navController
                    )
                }
                item { GallerySection(animatedVisibilityScope = animatedVisibilityScope, details = details, personalityInfo = personalityInfo) }
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        } else {
            Box(Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Detail untuk tipe $typeId tidak ditemukan.")
            }
        }
    }

    if (showLabelDialog) {
        AddFavoriteLabelDialog(
            currentLabel = currentLabel,
            onDismiss = { showLabelDialog = false },
            onConfirm = { label ->
                meViewModel.addOrUpdateFavorite(typeId, label)
                showLabelDialog = false
            }
        )
    }
}

@Composable
fun DetailTextHeader(details: PersonalityDetails, personalityInfo: PersonalityInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(colors = listOf(Color.White, Color(0xFFFBFBFF))))
            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = details.typeName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = "\"${personalityInfo.title}\"", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailTabs(
    details: PersonalityDetails,
    bingoTraits: List<BingoTrait>,
    themeColor: Color,
    navController: NavController
) {
    val tabs = listOf("Kekuatan", "Kelemahan", "Karier", "Bingo", "Hubungan", "Saran", "Relasi")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = Color.Transparent,
        contentColor = themeColor,
        edgePadding = 16.dp,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                color = themeColor,
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
            .defaultMinSize(minHeight = 300.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.Top
    ) { page ->
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            when (page) {
                0 -> ContentCard { InfoList("Kekuatan Utama", details.strengths, themeColor) }
                1 -> ContentCard { InfoList("Potensi Kelemahan", details.weaknesses, themeColor) }
                2 -> ContentCard { InfoList("Saran Jenjang Karier", details.careerPaths, themeColor) }
                3 -> BingoCard(traits = bingoTraits, themeColor = themeColor)
                4 -> ContentCard { InfoParagraph("Dalam Hubungan", details.relationships) }
                5 -> ContentCard { InfoList("Tips Pengembangan Diri", details.developmentTips, themeColor) }
                6 -> RelationshipTabContent(currentType = details.typeName.substringBefore(" "), navController = navController)
            }
        }
    }
}

@Composable
fun BingoCard(traits: List<BingoTrait>, themeColor: Color) {
    val gridItems = traits.take(16).toMutableList()
    val selectedItems = remember { mutableStateMapOf<BingoTrait, Boolean>() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Seberapa Kamu Banget?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Klik kotak yang paling menggambarkan dirimu!", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            ) {
                gridItems.chunked(4).forEach { rowItems ->
                    Row(Modifier.weight(1f)) {
                        rowItems.forEach { trait ->
                            val isSelected = selectedItems[trait] ?: false
                            BingoCell(
                                text = trait.text,
                                isSelected = isSelected,
                                themeColor = themeColor,
                                onClick = {
                                    selectedItems[trait] = !isSelected
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.BingoCell(
    text: String,
    isSelected: Boolean,
    themeColor: Color,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) themeColor.copy(alpha = 0.9f) else Color.Transparent,
        animationSpec = tween(300),
        label = "BingoCellBackground"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.DarkGray,
        animationSpec = tween(300),
        label = "BingoCellText"
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .border(0.5.dp, Color.LightGray.copy(alpha = 0.5f))
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 15.sp,
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Composable
fun ContentCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            content = content
        )
    }
}

@Composable
fun RelationshipTabContent(currentType: String, navController: NavController) {
    val allTypes = personalityGroupsForList.flatMap { it.types }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        allTypes.forEach { typeInfo ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("relationship_detail/${currentType}/${typeInfo.typeName}")
                    },
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        model = typeInfo.cardImageRes,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = typeInfo.typeName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = typeInfo.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Lihat Relasi dengan ${typeInfo.typeName}",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GallerySection(
    animatedVisibilityScope: AnimatedVisibilityScope,
    details: PersonalityDetails,
    personalityInfo: PersonalityInfo
) {
    val mainImage = if (details.detailImages.isNotEmpty()) details.detailImages.first() else R.drawable.placeholder
    val otherImages = if (details.detailImages.size > 1) details.detailImages.subList(1, details.detailImages.size) else emptyList()

    Column(modifier = Modifier
        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth()) {
        Text(
            "Galeri",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = mainImage),
            contentDescription = "Gambar Utama ${details.typeName}",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .sharedElement(
                    rememberSharedContentState(key = "image-${personalityInfo.typeName}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
        )
        otherImages.forEachIndexed { index, imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Gambar Galeri ${details.typeName} #${index + 2}",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
fun InfoList(title: String, items: List<String>, themeColor: Color) {
    Column {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        items.forEach { item ->
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(bottom = 12.dp)) {
                Text("•", modifier = Modifier.padding(end = 12.dp), fontSize = 16.sp, color = themeColor, fontWeight = FontWeight.Bold)
                Text(item, style = MaterialTheme.typography.bodyLarge, lineHeight = 24.sp)
            }
        }
    }
}

@Composable
fun InfoParagraph(title: String, text: String) {
    Column {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge, lineHeight = 24.sp, textAlign = TextAlign.Justify)
    }
}
