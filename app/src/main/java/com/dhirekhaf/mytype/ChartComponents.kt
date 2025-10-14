// File: app/src/main/java/ com/dhirekhaf/mytype/ChartComponents.kt
// [BERKAS BARU - SUMBER KEBENARAN UNTUK BAGAN]

package com.dhirekhaf.mytype

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class DimensionInfo(
    val scoreKey: Char,
    val leftTraitLabel: String,val rightTraitLabel: String,
    val leftTraitLetter: Char,
    val rightTraitLetter: Char
)

// PASTIKAN FUNGSI INI TIDAK PRIVATE
@Composable
fun DimensionChart(
    mbtiType: String,
    scores: Map<Char, Int>,
    theme: GroupTheme
) {
    if (mbtiType.length != 4) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(                "Bagan Dimensi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,                modifier = Modifier.padding(bottom = 12.dp)
            )

            val dimensionInfoList = listOf(
                DimensionInfo('E', "Introvert", "Ekstrovert", 'I', 'E'),
                DimensionInfo('S', "Intuition", "Sensing", 'N', 'S'),
                DimensionInfo('T', "Feeling", "Thinking", 'F', 'T'),
                DimensionInfo('J', "Perceiving", "Judging", 'P', 'J')
            )

            dimensionInfoList.forEachIndexed { index, dimension ->
                val dominantLetterInType = mbtiType[index]
                val score = scores[dimension.scoreKey] ?:50
                val isRightTraitDominant = dominantLetterInType == dimension.rightTraitLetter

                val finalProgress: Float
                val modifierScale: Modifier

                if (isRightTraitDominant) {
                    finalProgress = score / 100f
                    modifierScale = Modifier.scale(scaleX = -1f, scaleY = 1f)
                } else  {
                    finalProgress = (100 - score) / 100f
                    modifierScale = Modifier
                }

                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text(
                            text = dimension.leftTraitLabel,                            fontWeight = if (!isRightTraitDominant) FontWeight.Bold else FontWeight.Normal,
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
                        modifier = modifierScale
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










