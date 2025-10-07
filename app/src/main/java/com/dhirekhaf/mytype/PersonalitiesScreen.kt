// File: app/src/main/java/com/dhirekhaf/mytype/PersonalitiesScreen.kt

package com.dhirekhaf.mytype

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// --- [PERUBAHAN] DATA WARNA DAN FUNGSI UTILITAS BARU ---
data class GroupTheme(val primaryColor: Color, val secondaryColor: Color)

private val analystTheme = GroupTheme(Color(0xff735283), Color(0xFFD0BCFF))
private val diplomatTheme = GroupTheme(Color(0xFF3B7A57), Color(0xFFC3F3D0))
private val sentinelTheme = GroupTheme(Color(0xFF004A7F), Color(0xFFA5D8FF))
private val explorerTheme = GroupTheme(Color(0xFFB8860B), Color(0xFFFFE082))

/**
 * Fungsi utilitas untuk mendapatkan tema warna berdasarkan nama grup.
 */
fun getThemeForMbtiGroup(groupTitle: String): GroupTheme {
    return when (groupTitle) {
        "Para Analis" -> analystTheme
        "Para Diplomat" -> diplomatTheme
        "Para Penjaga" -> sentinelTheme
        "Para Perajin" -> explorerTheme
        else -> GroupTheme(Color.Gray, Color.LightGray) // Default
    }
}
// --- AKHIR PERUBAHAN ---

// Struktur data lain tidak berubah
data class PersonalityType(
    val name: String,
    val title: String,
    @DrawableRes val imageRes: Int,
    @DrawableRes val sliceImage: Int
)

data class PersonalityGroupData(
    val groupTitle: String,
    val backgroundImageRes: Int,
    val types: List<PersonalityType>
)

val personalityGroups = listOf(
    PersonalityGroupData(
        groupTitle = "Para Analis",
        backgroundImageRes = R.drawable.latarbodyungu,
        types = listOf(
            PersonalityType("INTJ", "Sang Arsitek", R.drawable.intj, R.drawable.intjslice),
            PersonalityType("INTP", "Sang Ahli Logika", R.drawable.intp, R.drawable.intpslice),
            PersonalityType("ENTJ", "Sang Komandan", R.drawable.entj, R.drawable.entjslice),
            PersonalityType("ENTP", "Sang Pendebat", R.drawable.entp, R.drawable.entpslice)
        )
    ),
    PersonalityGroupData(
        groupTitle = "Para Diplomat",
        backgroundImageRes = R.drawable.latarbodyhijau,
        types = listOf(
            PersonalityType("INFJ", "Sang Advokat", R.drawable.infj, R.drawable.infjslice),
            PersonalityType("INFP", "Sang Mediator", R.drawable.infp, R.drawable.infpslice),
            PersonalityType("ENFJ", "Sang Protagonis", R.drawable.enfj, R.drawable.enfjslice),
            PersonalityType("ENFP", "Sang Juru Kampanye", R.drawable.enfp, R.drawable.enfpslice)
        )
    ),
    PersonalityGroupData(
        groupTitle = "Para Penjaga",
        backgroundImageRes = R.drawable.latarbodybiru,
        types = listOf(
            PersonalityType("ISTJ", "Sang Ahli Logistik", R.drawable.istj, R.drawable.istjslice),
            PersonalityType("ISFJ", "Sang Pembela", R.drawable.isfj, R.drawable.isfjslice),
            PersonalityType("ESTJ", "Sang Eksekutif", R.drawable.estj, R.drawable.estjslice),
            PersonalityType("ESFJ", "Sang Konsul", R.drawable.esfj, R.drawable.esfjslice)
        )
    ),
    PersonalityGroupData(
        groupTitle = "Para Perajin",
        backgroundImageRes = R.drawable.latarbodykuning,
        types = listOf(
            PersonalityType("ISTP", "Sang Virtuoso", R.drawable.istp, R.drawable.istpslice),
            PersonalityType("ISFP", "Sang Petualang", R.drawable.isfp, R.drawable.isfpslice),
            PersonalityType("ESTP", "Sang Pengusaha", R.drawable.estp, R.drawable.estpslice),
            PersonalityType("ESFP", "Sang Penghibur", R.drawable.esfp, R.drawable.esfpslice)
        )
    )
)

private val PersonalitiesGayaHeader = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
private val PersonalitiesGayaJudulGrup = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
private val PersonalitiesGayaTipeNama = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
private val PersonalitiesGayaTipeJudul = TextStyle(fontSize = 14.sp, color = Color.DarkGray)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PersonalitiesScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavController,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                PersonalitiesHeader()
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(
                items = personalityGroups,
                key = { it.groupTitle }
            ) { group ->
                PersonalityGroup(
                    animatedVisibilityScope = animatedVisibilityScope,
                    group = group,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        ModernBottomNavBar(
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun PersonalitiesHeader() {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.latarponihijau),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Text(
                text = "TIPE KEPRIBADIAN",
                style = PersonalitiesGayaHeader,
            )
        }
        Image(
            painter = painterResource(id = R.drawable.typeall),
            contentDescription = "All Personality Types Illustration",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 32.dp, horizontal = 24.dp)
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.PersonalityGroup(
    animatedVisibilityScope: AnimatedVisibilityScope,
    group: PersonalityGroupData,
    navController: NavController
) {
    val groupImageRes = when (group.groupTitle) {
        "Para Analis" -> R.drawable.theanalysts
        "Para Diplomat" -> R.drawable.thediplomats
        "Para Penjaga" -> R.drawable.thesentinels
        "Para Perajin" -> R.drawable.theexplorers
        else -> 0
    }

    // Dapatkan warna tema untuk grup ini
    val theme = getThemeForMbtiGroup(group.groupTitle)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        if (groupImageRes != 0) {
            Image(
                painter = painterResource(id = groupImageRes),
                contentDescription = group.groupTitle,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(220.dp)
                    .offset(y = (-20).dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)
                .defaultMinSize(minHeight = 520.dp)
        ) {
            Image(
                painter = painterResource(id = group.backgroundImageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = group.groupTitle,
                    style = PersonalitiesGayaJudulGrup,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    group.types.forEach { personality ->
                        PersonalityCard(
                            animatedVisibilityScope = animatedVisibilityScope,
                            personality = personality,
                            navController = navController,
                            theme = theme // Kirim tema ke kartu
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.PersonalityCard(
    animatedVisibilityScope: AnimatedVisibilityScope,
    personality: PersonalityType,
    navController: NavController,
    theme: GroupTheme // Terima tema sebagai parameter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // --- [PERUBAHAN NAVIGASI] Kirim warna primer sebagai argumen ---
                // Mengubah nilai Color menjadi Hex String untuk dikirim
                val colorHex = String.format("%08X", theme.primaryColor.toArgb())
                navController.navigate("personality_detail/${personality.name}/${colorHex}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)
            ) {
                Text(text = personality.name, style = PersonalitiesGayaTipeNama)
                Text(text = personality.title, style = PersonalitiesGayaTipeJudul)
            }
            Image(
                painter = painterResource(id = personality.imageRes),
                contentDescription = personality.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(170.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .sharedElement(
                        rememberSharedContentState(key = "image-${personality.name}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
            )
        }
    }
}
