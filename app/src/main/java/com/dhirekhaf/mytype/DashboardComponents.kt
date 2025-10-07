// File: app/src/main/java/com/dhirekhaf/mytype/DashboardComponents.kt
// [KODE FINAL v3.2 - MEMPERBAIKI FORCE CLOSE SAAT NAVIGASI DETAIL]

package com.dhirekhaf.mytype

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhirekhaf.mytype.data.UserData
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ResultDashboard(
    userData: UserData,
    personalityData: PersonalityInfo,
    groupData: PersonalityGroup,
    onNavigateToDetail: (typeId: String, themeColorHex: String) -> Unit,
    onNavigateToRelation: (type1: String, type2: String) -> Unit
) {
    val theme = getThemeForMbtiGroup(groupData.groupTitle)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InfoBox("Julukan", personalityData.title, Icons.Default.Info, theme, Modifier.weight(1f))
        InfoBox("Kelompok", groupData.groupTitle, Icons.Default.Shield, theme, Modifier.weight(1f))
    }
    DimensionChart(mbtiType = userData.mbtiType, scores = userData.dimensionScores, theme = theme)
    DynamicLearnMoreButton(theme = theme) {
        // [PERBAIKAN] Langsung gunakan properti hexColor yang sudah aman dari GroupTheme.
        // Tidak ada lagi konversi yang berisiko.
        onNavigateToDetail(userData.mbtiType, theme.hexColor)
    }
    RelationshipCard(groupData) { onNavigateToRelation(userData.mbtiType, it) }
    if (userData.hobbies.isNotEmpty()) {
        HobbyCard(userData.hobbies, theme)
    }
}

@Composable
fun DynamicLearnMoreButton(theme: GroupTheme, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.primaryColor.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Pelajari Tipe Anda Lebih Lanjut",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun InfoBox(title: String, value: String, icon: ImageVector, theme: GroupTheme, modifier: Modifier) {
    Row(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.1f))
            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, null, tint = theme.primaryColor)
        Column {
            Text(title, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun HobbyChip(hobby: String, theme: GroupTheme) {
    Box(
        Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.Transparent)
            .border(1.dp, theme.primaryColor.copy(alpha = 0.5f), CircleShape)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(hobby, color = Color.White.copy(alpha = 0.9f), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
fun HobbyCard(hobbies: Set<String>, theme: GroupTheme) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.2f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("Minat & Hobi", fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 16.dp))
            FlowRow(Modifier.fillMaxWidth(), mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
                hobbies.forEach { HobbyChip(it, theme) }
            }
        }
    }
}

@Composable
fun RelationshipCard(groupData: PersonalityGroup, onNavigateToRelation: (String) -> Unit) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.2f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("Sorotan Hubungan", fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 16.dp))
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceAround, Alignment.CenterVertically) {
                RelationshipHighlightItem("Pasangan Ideal", groupData.idealPartner, { Icon(Icons.Default.Favorite, null, tint = Color(0xFFE91E63)) }, onNavigateToRelation)
                RelationshipHighlightItem("Pasangan Baik", groupData.goodPartner, { Icon(Icons.Default.Spa, null, tint = Color(0xFF4CAF50)) }, onNavigateToRelation)
            }
        }
    }
}

@Composable
fun RowScope.RelationshipHighlightItem(label: String, partnerType: String?, icon: @Composable () -> Unit, onClick: (String) -> Unit) {
    Column(
        Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(partnerType != null) { partnerType?.let(onClick) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        icon()
        Text(label, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
        Text(partnerType ?: "N/A", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = if (partnerType != null) Color.White else Color.Gray)
    }
}

@Composable
fun NoTestResultCard(onTakeTestClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.3f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Temukan Tipe Kepribadian Anda", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.White)
            Text("Ikuti tes untuk melihat dasbor lengkap dan menemukan jati diri Anda!", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, color = Color.White.copy(alpha = 0.8f))
            Button(onClick = onTakeTestClick, colors = ButtonDefaults.buttonColors(Color.White)) {
                Text("Mulai Tes Kepribadian", color = Color.Black)
            }
        }
    }
}

private data class DimensionInfo(
    val scoreKey: Char,
    val leftTraitLabel: String,
    val rightTraitLabel: String,
    val leftTraitLetter: Char,
    val rightTraitLetter: Char
)

@Composable
fun DimensionChart(mbtiType: String, scores: Map<Char, Int>, theme: GroupTheme) {
    if (mbtiType.length != 4) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Bagan Dimensi", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 12.dp))

            val dimensionInfoList = listOf(
                DimensionInfo('E', "Introvert", "Ekstrovert", 'I', 'E'),
                DimensionInfo('S', "Intuition", "Sensing",   'N', 'S'),
                DimensionInfo('T', "Feeling", "Thinking",     'F', 'T'),
                DimensionInfo('J', "Perceiving", "Judging",      'P', 'J')
            )

            dimensionInfoList.forEachIndexed { index, dimension ->
                val dominantLetterInType = mbtiType[index]
                val score = scores[dimension.scoreKey] ?: 50
                val isRightTraitDominant = dominantLetterInType == dimension.rightTraitLetter

                val finalProgress: Float
                val modifierScale: Modifier

                if (isRightTraitDominant) {
                    // HASIL DOMINAN KANAN (E, S, T, J)
                    // Visual: Bar "tumbuh" dari KANAN ke KIRI
                    finalProgress = score / 100f
                    modifierScale = Modifier.scale(scaleX = -1f, scaleY = 1f) // Balik bar secara horizontal
                } else {
                    // HASIL DOMINAN KIRI (I, N, F, P)
                    // Visual: Bar "tumbuh" dari KIRI ke KANAN
                    finalProgress = (100 - score) / 100f
                    modifierScale = Modifier // Tanpa pembalikan
                }

                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text(
                            text = dimension.leftTraitLabel,
                            fontWeight = if (!isRightTraitDominant) FontWeight.Bold else FontWeight.Normal,
                            color = if (!isRightTraitDominant) Color.White else Color.White.copy(alpha = 0.6f)
                        )
                        Text(
                            text = dimension.rightTraitLabel,
                            fontWeight = if (isRightTraitDominant) FontWeight.Bold else FontWeight.Normal,
                            color = if (isRightTraitDominant) Color.White else Color.White.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { finalProgress },
                        modifier = modifierScale // Terapkan modifier pembalik di sini
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = theme.primaryColor,
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}
