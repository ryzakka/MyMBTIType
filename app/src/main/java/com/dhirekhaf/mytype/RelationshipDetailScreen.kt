// File: app/src/main/java/com/dhirekhaf/mytype/RelationshipDetailScreen.kt

package com.dhirekhaf.mytype

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // vvv TAMBAHKAN IMPOR INI vvv
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // vvv TAMBAHKAN IMPOR INI vvv
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dhirekhaf.mytype.data.UserDataRepository // vvv TAMBAHKAN IMPOR INI vvv

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelationshipDetailScreen(
    type1: String,
    type2: String,
    navController: NavController
) {
    // vvv TAMBAHKAN BAGIAN INI UNTUK MENGAMBIL VIEWMODEL & USERDATA vvv
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by meViewModel.userData.collectAsState()
    // ^^^ AKHIR BAGIAN BARU ^^^

    val details1 = personalityDetailsMap[type1]
    val details2 = personalityDetailsMap[type2]

    val personalityInfo1 = personalityGroupsForList.flatMap { it.types }.find { it.typeName == type1 }
    val personalityInfo2 = personalityGroupsForList.flatMap { it.types }.find { it.typeName == type2 }
    val group1 = personalityGroupsForList.find { g -> g.types.any { it.typeName == type1 } }
    val group2 = personalityGroupsForList.find { g -> g.types.any { it.typeName == type2 } }

    val theme1 = getThemeForMbtiGroup(group1?.groupTitle ?: "")
    val theme2 = getThemeForMbtiGroup(group2?.groupTitle ?: "")

    var relation1to2 = details1?.relationshipDetails?.find { it.otherTypeName == type2 }
    if (relation1to2 == null) {
        relation1to2 = details2?.relationshipDetails?.find { it.otherTypeName == type1 }
    }

    val view1on2 = details1?.viewsFromOthers?.find { it.typeName == type2 }?.view ?: "Pandangan tidak tersedia."
    val view2on1 = details2?.viewsFromOthers?.find { it.typeName == type1 }?.view ?: "Pandangan tidak tersedia."

    // vvv TAMBAHKAN LOGIKA UNTUK FAVORIT vvv
    // Buat kunci yang konsisten untuk memeriksa status favorit
    val relationKey = listOf(type1, type2).sorted().joinToString("-")
    val isFavorite = userData.favoriteRelations.contains(relationKey)
    // ^^^ AKHIR BAGIAN BARU ^^^

    Scaffold(
        topBar = {
            // [MODIFIKASI] TopAppBar sekarang berada di dalam Scaffold
            TopAppBar(
                title = { Text("$type1 & $type2", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                actions = {
                    // vvv INI TOMBOL FAVORIT BARU KITA vvv
                    IconButton(onClick = {
                        meViewModel.toggleFavoriteRelation(type1, type2)
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = "Jadikan Favorit",
                            tint = if (isFavorite) Color(0xFFFFD700) else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent // Membuat TopAppBar transparan
                )
            )
        },
        containerColor = Color(0xFFF2F3F7)
    ) { paddingValues ->
        // [MODIFIKASI] Kita bungkus LazyColumn dengan Box agar TopAppBar bisa menumpuk di atasnya
        Box(modifier = Modifier.fillMaxSize()) {
            if (relation1to2 != null && personalityInfo1 != null && personalityInfo2 != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    // Hapus paddingValues, karena header akan mengisi area di bawah status bar
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    item {
                        RelationshipHeader(
                            typeInfo1 = personalityInfo1,
                            typeInfo2 = personalityInfo2,
                            theme1 = theme1,
                            theme2 = theme2,
                        )
                    }

                    // ... (Sisa item di LazyColumn tetap sama)
                    item {
                        Column(Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
                            PerspectiveCard(
                                title = "Bagaimana $type1 Melihat $type2",
                                perspective = view1on2,
                                themeColor = theme1.primaryColor
                            )
                            Spacer(Modifier.height(16.dp))
                            PerspectiveCard(
                                title = "Bagaimana $type2 Melihat $type1",
                                perspective = view2on1,
                                themeColor = theme2.primaryColor
                            )
                        }
                    }
                    item {
                        Column(Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
                            RelationshipAspectCard(
                                title = "Kekuatan Hubungan",
                                icon = Icons.Default.Favorite,
                                items = relation1to2.strengths,
                                iconColor = Color(0xFF2E7D32)
                            )
                            Spacer(Modifier.height(16.dp))
                            RelationshipAspectCard(
                                title = "Potensi Tantangan",
                                icon = Icons.Default.Warning,
                                items = relation1to2.challenges,
                                iconColor = Color(0xFFF9A825)
                            )
                            Spacer(Modifier.height(16.dp))
                            RelationshipAspectCard(
                                title = "Saran untuk Harmoni",
                                icon = Icons.Default.SyncAlt,
                                items = relation1to2.advice,
                                iconColor = Color(0xFF1565C0)
                            )
                        }
                    }
                }
            } else {
                // Tampilan fallback tidak berubah
                Box(Modifier
                    .fillMaxSize()
                    .padding(paddingValues), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                        }
                        Text("Detail hubungan untuk $type1 dan $type2 belum tersedia.")
                    }
                }
            }
            // [MODIFIKASI] TopAppBar dipindahkan ke luar Box agar posisinya tetap di atas
        }
    }
}


// [MODIFIKASI] Hilangkan parameter onBackClick karena sudah ditangani TopAppBar
@Composable
fun RelationshipHeader(
    typeInfo1: PersonalityInfo,
    typeInfo2: PersonalityInfo,
    theme1: GroupTheme,
    theme2: GroupTheme,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                Brush.linearGradient(
                    colors = listOf(theme1.primaryColor, theme2.primaryColor)
                )
            )
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        // Tombol kembali sudah tidak ada di sini lagi
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tipe 1
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = typeInfo1.cardImageRes,
                    contentDescription = typeInfo1.typeName,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(
                                3.dp,
                                Color.White.copy(alpha = 0.8f)
                            ), CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(8.dp))
                Text(typeInfo1.typeName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            // Simbol '&'
            Text("&", color = Color.White.copy(alpha = 0.9f), fontSize = 48.sp, fontWeight = FontWeight.Light)

            // Tipe 2
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = typeInfo2.cardImageRes,
                    contentDescription = typeInfo2.typeName,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(
                                3.dp,
                                Color.White.copy(alpha = 0.8f)
                            ), CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(8.dp))
                Text(typeInfo2.typeName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}


// ... Sisa Composable (PerspectiveCard, RelationshipAspectCard) tidak perlu diubah ...
@Composable
fun PerspectiveCard(title: String, perspective: String, themeColor: Color) {
    Column {
        Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Spacer(Modifier.height(8.dp))
        Row {
            Box(
                Modifier
                    .width(4.dp)
                    .height(50.dp) // Sesuaikan tinggi sesuai kebutuhan
                    .background(themeColor, shape = RoundedCornerShape(2.dp))
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "\"$perspective\"",
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun RelationshipAspectCard(
    title: String,
    icon: ImageVector,
    items: List<String>,
    iconColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
                Spacer(Modifier.width(12.dp))
                Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items.forEach { item ->
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            "•",
                            color = iconColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(item, style = MaterialTheme.typography.bodyLarge, lineHeight = 24.sp)
                    }
                }
            }
        }
    }
}
