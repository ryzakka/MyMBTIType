// Lokasi: app/src/main/java/com/dhirekhaf/mytype/ProfileShareCard.kt

package com.dhirekhaf.mytype

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Interests
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhirekhaf.mytype.data.UserData
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun ProfileShareCard(
    userData: UserData,
    personalityInfo: PersonalityInfo,
    groupData: PersonalityGroup,
    theme: GroupTheme
) {
    val personalityDetails = personalityDetailsMap[personalityInfo.typeName]

    Box(modifier = Modifier.background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top,
                modifier = Modifier.weight(0.4f) // Beri bobot lebih untuk bagian ini
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (personalityDetails != null) {
                        InfoCard(useFullHeight = true) {
                            GroupDescriptionSection(personalityDetails, theme)
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    InfoCard(useFullHeight = true) {
                        UserBioSection(userData = userData, theme = theme)
                    }
                }
            }
            Row(modifier = Modifier.weight(0.35f)) {
                InfoCard(useFullHeight = true) {
                    ChartSection(userData)
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top,
                modifier = Modifier.weight(0.25f) // Bobot lebih kecil
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    InfoCard(useFullHeight = true) {
                        RelationsSection(groupData)
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    InfoCard(useFullHeight = true) {
                        HobbiesSection(
                            userData = userData,
                            theme = theme,
                            footerContent = { PersonalFooter(userData) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(
    useFullHeight: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val modifier = if (useFullHeight) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Column(
            modifier = Modifier.padding(12.dp), // Kurangi padding dalam kartu
            content = content
        )
    }
}

@Composable
private fun ChartSection(userData: UserData) {
    SectionHeader(icon = { Icon(Icons.Outlined.Analytics, contentDescription = null, tint = Color.Gray) }, title = "Skor Dimensi")
    Spacer(modifier = Modifier.height(8.dp))
    SmallDimensionChart(
        scores = userData.dimensionScores,
        mbtiType = userData.mbtiType
    )
}

@Composable
private fun RelationsSection(groupData: PersonalityGroup) {
    SectionHeader(icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null, tint = Color(0xFFE91E63)) }, title = "Koneksi")
    Spacer(modifier = Modifier.height(8.dp))
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InfoChip(label = "Ideal", value = groupData.idealPartner ?: "N/A")
        InfoChip(label = "Baik", value = groupData.goodPartner ?: "N/A")
    }
}

@Composable
private fun HobbiesSection(
    userData: UserData,
    theme: GroupTheme,
    footerContent: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            SectionHeader(icon = { Icon(Icons.Outlined.Interests, contentDescription = null, tint = theme.primaryColor) }, title = "Minat")
            if (userData.hobbies.isEmpty()){
                Text("Belum ada hobi.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    mainAxisSpacing = 6.dp,
                    crossAxisSpacing = 6.dp,
                ) {
                    userData.hobbies.take(5).forEach { hobby ->
                        HobbyChip(hobby = hobby, theme = theme)
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            footerContent()
        }
    }
}

@Composable
private fun GroupDescriptionSection(details: PersonalityDetails, theme: GroupTheme) {
    SectionHeader(icon = { Icon(Icons.Outlined.Lightbulb, contentDescription = null, tint = theme.primaryColor) }, title = "Sekilas Tipe")
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = details.description,
        style = MaterialTheme.typography.bodySmall,
        color = Color.DarkGray,
        lineHeight = 15.sp,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun UserBioSection(userData: UserData, theme: GroupTheme) {
    SectionHeader(icon = { Icon(Icons.Outlined.PersonOutline, contentDescription = null, tint = theme.primaryColor) }, title = "Bio")
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = if (userData.bio.isNotBlank()) userData.bio else "Pengguna ini belum menulis bio.",
        style = MaterialTheme.typography.bodySmall,
        color = if (userData.bio.isNotBlank()) Color.DarkGray else Color.Gray,
        lineHeight = 15.sp,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
private fun PersonalFooter(userData: UserData) {
    Text(
        text = userData.name.ifEmpty { "Pengguna" },
        fontSize = 9.sp,
        color = Color.LightGray,
        textAlign = TextAlign.End
    )
}


@Composable
private fun SectionHeader(icon: @Composable () -> Unit, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        icon()
        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun InfoChip(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}


@Composable
private fun SmallDimensionChart(scores: Map<Char, Int>, mbtiType: String) {
    val dimensionThemes = mapOf('E' to analystTheme, 'S' to diplomatTheme, 'T' to sentinelTheme, 'J' to explorerTheme)
    val dimensions = listOf('E', 'S', 'T', 'J')
    val labels = mapOf('E' to Pair("E", "I"), 'S' to Pair("S", "N"), 'T' to Pair("T", "F"), 'J' to Pair("J", "P"))

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dimensions.forEach { key ->
            val score = scores[key] ?: 50
            val progress = score / 100f
            val (firstLabel, secondLabel) = labels[key]!!
            val barTheme = dimensionThemes[key] ?: analystTheme
            val firstPercentage = score
            val secondPercentage = 100 - score
            val isFirstDominant = mbtiType.contains(firstLabel)

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = firstLabel, fontSize = 10.sp, color = if (isFirstDominant) barTheme.primaryColor else Color.Gray, fontWeight = if (isFirstDominant) FontWeight.Bold else FontWeight.Normal)
                Text(text = "$firstPercentage%", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape), // Tipiskan bar
                    color = barTheme.primaryColor,
                    trackColor = barTheme.primaryColor.copy(alpha = 0.2f)
                )
                Text(text = "$secondPercentage%", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                Text(text = secondLabel, fontSize = 10.sp, color = if (!isFirstDominant) barTheme.primaryColor else Color.Gray, fontWeight = if (!isFirstDominant) FontWeight.Bold else FontWeight.Normal)
            }
        }
    }
}

