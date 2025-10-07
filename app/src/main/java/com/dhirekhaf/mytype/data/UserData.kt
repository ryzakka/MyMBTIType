// File: app/src/main/java/com/dhirekhaf/mytype/data/UserData.kt
package com.dhirekhaf.mytype.data

import android.net.Uri

data class UserData(
    val name: String = "",
    val mbtiType: String = "",
    val imageUri: Uri? = null,
    // --- TAMBAHKAN BARIS INI ---
    val dimensionScores: Map<Char, Int> = emptyMap()
)
