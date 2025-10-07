// File: app/src/main/java/com/dhirekhaf/mytype/data/UserData.kt

package com.dhirekhaf.mytype.data

import android.net.Uri

data class UserData(
    val name: String,
    val mbtiType: String,
    val imageUri: Uri?,
    val dimensionScores: Map<Char, Int>,
    val isDataLoaded: Boolean = false,

    // [PERUBAHAN] Tambahkan properti baru
    val email: String,
    val bio: String,
    val hobbies: List<String>
)
