// File: app/src/main/java/com/dhirekhaf/mytype/EditProfileScreen.kt

package com.dhirekhaf.mytype

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow

val availableHobbies = listOf(
    "Membaca", "Olahraga", "Musik", "Film", "Game", "Traveling", "Memasak", "Seni"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    name: String,
    email: String,
    bio: String,
    hobbies: List<String>,
    imageUri: Uri?,
    theme: GroupTheme,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onHobbyToggle: (String) -> Unit,
    onImageClick: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = theme.primaryColor

    Box(modifier = Modifier.fillMaxSize()) {
        val backgroundBrush = Brush.verticalGradient(
            colors = listOf(
                accentColor.copy(alpha = 0.5f),
                Color.Black
            )
        )
        Box(modifier = Modifier.fillMaxSize().background(backgroundBrush))

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Edit Profil", fontWeight = FontWeight.Bold, color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    navigationIcon = {
                        TextButton(onClick = onCancel) { Text("Batal", color = Color.White) }
                    },
                    actions = {
                        TextButton(onClick = onSave) { Text("Simpan", fontWeight = FontWeight.Bold, color = Color.White) }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clickable(onClick = onImageClick),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = imageUri ?: R.drawable.ic_account_circle_24,
                        contentDescription = "Gambar Profil",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .border(
                                width = 3.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(accentColor, theme.secondaryColor)
                                ),
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.ic_account_circle_24)
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .align(Alignment.BottomEnd)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Ubah Gambar",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                val textFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.15f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                    disabledContainerColor = Color.White.copy(alpha = 0.1f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White.copy(alpha = 0.8f),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.6f)
                )
                val textFieldShape = RoundedCornerShape(16.dp)

                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Nama") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = textFieldShape,
                    singleLine = true,
                    colors = textFieldColors
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = textFieldShape,
                    singleLine = true,
                    colors = textFieldColors
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = bio,
                    onValueChange = onBioChange,
                    label = { Text("Bio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = textFieldShape,
                    colors = textFieldColors
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- Bagian Hobi dengan Gaya Baru ---
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Pilih Minat & Hobi Anda",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Teks putih
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp
                    ) {
                        availableHobbies.forEach { hobby ->
                            HobbyChipSelector(
                                text = hobby,
                                isSelected = hobbies.contains(hobby),
                                theme = theme,
                                onClick = { onHobbyToggle(hobby) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun HobbyChipSelector(
    text: String,
    isSelected: Boolean,
    theme: GroupTheme,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) theme.primaryColor else Color.White.copy(alpha = 0.1f)
    val contentColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f)
    val border = if (isSelected) BorderStroke(1.dp, Color.Transparent) else BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .border(border, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Terpilih",
                    tint = contentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = text,
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
