// File: app/src/main/java/com/dhirekhaf/mytype/EditProfileScreen.kt
// [PERBAIKAN] Menambahkan Spacer di bagian bawah untuk ruang napas.

package com.dhirekhaf.mytype

import android.net.Uri
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.sp
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profil", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = { TextButton(onClick = onCancel) { Text("Batal", color = accentColor) } },
                actions = { TextButton(onClick = onSave) { Text("Simpan", fontWeight = FontWeight.Bold, color = accentColor) } }
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    accentColor.copy(alpha = 0.05f),
                    Color(0xFFF7F7FD)
                )
            )
        )
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

            // --- Bagian Foto Profil ---
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

            // --- Bagian Input Form ---
            val textFieldShape = RoundedCornerShape(16.dp)
            OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Nama") }, modifier = Modifier.fillMaxWidth(), shape = textFieldShape, singleLine = true)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = textFieldShape, singleLine = true)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = bio, onValueChange = onBioChange, label = { Text("Bio") }, modifier = Modifier
                .fillMaxWidth()
                .height(120.dp), shape = textFieldShape)

            Spacer(modifier = Modifier.height(32.dp))

            // --- Bagian Hobi ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Pilih Minat & Hobi Anda", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp
                ) {
                    availableHobbies.forEach { hobby ->
                        val isSelected = hobbies.contains(hobby)
                        HobbyChipSelector(
                            text = hobby,
                            isSelected = isSelected,
                            theme = theme,
                            onClick = { onHobbyToggle(hobby) }
                        )
                    }
                }
            }

            // [PERBAIKAN UTAMA] Menambahkan Spacer di sini untuk memberi ruang napas di bagian bawah.
            Spacer(modifier = Modifier.height(32.dp))
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
    val backgroundColor = if (isSelected) theme.primaryColor else Color.Transparent
    val contentColor = if (isSelected) Color.White else theme.primaryColor
    val border = if (isSelected) BorderStroke(1.dp, Color.Transparent) else BorderStroke(1.dp, theme.primaryColor.copy(alpha = 0.4f))

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
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}
