// Lokasi: app/src/main/java/com/dhirekhaf/mytype/FavoritesScreen.kt

package com.dhirekhaf.mytype

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dhirekhaf.mytype.data.UserDataRepository

@Composable
fun FavoritesScreen(
    navController: NavController,
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by meViewModel.userData.collectAsState()

    val favoriteRelations = userData.favoriteRelations.toList()
    val favoriteTypesMap = userData.favoriteTypes

    val personalityTheme = getThemeForMbti(userData.mbtiType)

    var editingType by remember { mutableStateOf<Pair<String, String>?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition("ken_burns_favorites")
        val scale by infiniteTransition.animateFloat(1.0f, 1.1f, infiniteRepeatable(tween(20000, easing = LinearEasing), RepeatMode.Reverse), "scale_favorites")
        Image(painter = painterResource(personalityTheme.background), contentDescription = "Background", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().scale(scale))
        FloatingParticles(personalityTheme.particleColor)
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Black.copy(0.4f), Color.Transparent, Color.Black.copy(0.7f)))))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Koleksi Favorit",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                )
            }

            item { SectionTitle("Relasi Disimpan") }
            if (favoriteRelations.isEmpty()) {
                item { EmptyState("Anda belum menyimpan relasi apa pun.") }
            } else {
                items(favoriteRelations, key = { it }) { relationKey ->
                    val types = relationKey.split("-")
                    if (types.size == 2) {
                        FavoriteRelationCard(
                            type1 = types[0],
                            type2 = types[1],
                            onCardClick = { navController.navigate("relationship_detail/${types[0]}/${types[1]}") },
                            onDeleteClick = { meViewModel.toggleFavoriteRelation(types[0], types[1]) }
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
            item { SectionTitle("Tipe Kepribadian Disimpan") }
            if (favoriteTypesMap.isEmpty()) {
                item { EmptyState("Anda belum menyimpan tipe kepribadian apa pun.") }
            } else {
                items(favoriteTypesMap.toList(), key = { it.first }) { (typeName, label) ->
                    val personalityInfo = personalityGroupsForList.flatMap { it.types }.find { it.typeName == typeName }
                    val group = personalityGroupsForList.find { it.types.any { p -> p.typeName == typeName } }
                    if (personalityInfo != null && group != null) {
                        val theme = getThemeForMbtiGroup(group.groupTitle)
                        FavoriteTypeCard(
                            personalityInfo = personalityInfo,
                            label = label,
                            theme = theme,
                            onCardClick = {
                                val colorHex = String.format("%08X", theme.primaryColor.toArgb())
                                navController.navigate("personality_detail/${personalityInfo.typeName}/${colorHex}")
                            },
                            onEditClick = { editingType = typeName to label },
                            onDeleteClick = { meViewModel.removeFavorite(typeName) }
                        )
                    }
                }
            }
        }

        ModernBottomNavBar(
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        if (editingType != null) {
            AddFavoriteLabelDialog(
                currentLabel = editingType!!.second,
                onDismiss = { editingType = null },
                onConfirm = { newLabel ->
                    meViewModel.addOrUpdateFavorite(editingType!!.first, newLabel)
                    editingType = null
                }
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color.White.copy(alpha = 0.9f),
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Composable
private fun EmptyState(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White.copy(alpha = 0.7f),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    )
}

@Composable
private fun FavoriteRelationCard(
    type1: String,
    type2: String,
    onCardClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onCardClick)
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$type1  &  $type2", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Hapus Relasi",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun FavoriteTypeCard(
    personalityInfo: PersonalityInfo,
    label: String,
    theme: GroupTheme,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(theme.primaryColor))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "${personalityInfo.typeName} - ${personalityInfo.title}", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
            }
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Opsi", tint = Color.White.copy(alpha = 0.8f))
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    DropdownMenuItem(
                        text = { Text("Ubah Label") },
                        onClick = {
                            onEditClick()
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Hapus", color = MaterialTheme.colorScheme.error) },
                        onClick = {
                            onDeleteClick()
                            menuExpanded = false
                        }
                    )
                }
            }
        }
    }
}
