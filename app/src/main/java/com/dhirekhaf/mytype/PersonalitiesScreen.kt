// Lokasi: app/src/main/java/com/dhirekhaf/mytype/PersonalitiesScreen.kt

package com.dhirekhaf.mytype

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.forEach
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
            // Menggunakan data yang dirancang khusus untuk layar daftar
            items(
                items = personalityGroupsForList,
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
            Text(text = "TIPE KEPRIBADIAN", style = PersonalitiesGayaHeader)
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
    group: PersonalityGroup,
    navController: NavController
) {
    val theme = getThemeForMbtiGroup(group.groupTitle)

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)) {
        Image(
            painter = painterResource(id = group.groupHeaderImageRes),
            contentDescription = group.groupTitle,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(220.dp)
                .offset(y = (-20).dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)
                .defaultMinSize(minHeight = 420.dp) // Disesuaikan tingginya
        ) {
            Image(
                painter = painterResource(id = group.groupBackgroundImageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)) {
                Text(text = group.groupTitle, style = PersonalitiesGayaJudulGrup)
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    group.types.forEach { personality ->
                        PersonalityCard(
                            animatedVisibilityScope = animatedVisibilityScope,
                            personality = personality,
                            navController = navController,
                            theme = theme
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
    personality: PersonalityInfo, // Menggunakan PersonalityInfo
    navController: NavController,
    theme: GroupTheme
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val colorHex = String.format("%08X", theme.primaryColor.toArgb())
                // Mengirim tipe MBTI untuk pemetaan
                navController.navigate("personality_detail/${personality.typeName}/${colorHex}")
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
            Column(modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f)) {
                Text(text = personality.typeName, style = PersonalitiesGayaTipeNama)
                Text(text = personality.title, style = PersonalitiesGayaTipeJudul)
            }
            Image(
                painter = painterResource(id = personality.cardImageRes), // Menggunakan cardImageRes
                contentDescription = personality.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(170.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .sharedElement(
                        rememberSharedContentState(key = "image-${personality.typeName}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
            )
        }
    }
}
