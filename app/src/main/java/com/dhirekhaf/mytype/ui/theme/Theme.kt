// File: app/src/main/java/com/dhirekhaf/mytype/ui/theme/Theme.kt

package com.dhirekhaf.mytype.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Skema warna default untuk mode terang (Light Mode)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xff735283),       // Ungu primer Anda
    secondary = Color(0xFF3B7A57),     // Hijau sebagai sekunder
    tertiary = Color(0xFF4682B4),      // Biru sebagai tersier

    // --- [PERBAIKAN] Tambahkan warna-warna ini untuk FilterChip ---
    primaryContainer = Color(0xFFEADDFF),      // Warna kontainer saat primary aktif
    onPrimaryContainer = Color(0xFF28005A),    // Warna teks/ikon di atas primaryContainer
    secondaryContainer = Color(0xFFD9E7D8),    // Warna kontainer untuk secondary
    onSecondaryContainer = Color(0xFF161F1A),  // Warna teks/ikon di atas secondaryContainer
    // --- AKHIR PERBAIKAN ---

    /* Anda bisa menyesuaikan warna lain jika diperlukan
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// Skema warna default untuk mode gelap (Dark Mode) - bisa disesuaikan nanti
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFC9B9D5), // Versi lebih terang dari ungu primer
    secondary = Color(0xFFA3CBA7),
    tertiary = Color(0xFFB0C4DE)
)

@Composable
fun MyTypeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color hanya tersedia di Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asumsi Anda punya file Typography.kt
        content = content
    )
}
