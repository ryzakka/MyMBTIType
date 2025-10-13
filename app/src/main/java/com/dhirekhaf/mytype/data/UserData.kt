// Lokasi: app/src/main/java/com/dhirekhaf/mytype/data/UserData.kt

package com.dhirekhaf.mytype.data

/**
 * Data class yang merepresentasikan semua data pengguna yang disimpan.
 *
 * @param name Nama lengkap pengguna.
 * @param email Alamat email pengguna.
 * @param imageUri URI gambar profil dalam bentuk String, bisa null.
 * @param dimensionScores Peta skor dimensi MBTI (I, E, N, S, T, F, J, P).
 * @param hasCompletedTest Status apakah pengguna sudah menyelesaikan tes.
 * @param mbtiType Hasil tipe MBTI pengguna, cth: "INFP".
 * @param bio Teks bio singkat dari pengguna.
 * @param hobbies Kumpulan hobi pengguna. Menggunakan Set untuk menghindari duplikasi.
 * @param favoriteRelations Kumpulan relasi favorit, disimpan sebagai key "TIPE1-TIPE2".
 */
data class UserData(
    val name: String,
    val email: String,
    val imageUri: String?,
    val dimensionScores: Map<Char, Int>,
    val hasCompletedTest: Boolean,
    val mbtiType: String,
    val bio: String,
    val hobbies: Set<String>,
    val favoriteRelations: Set<String>,
    val favoriteTypes: Map<String, String>,
    val isDataLoaded: Boolean = false,
    val scores: MbtiScores = MbtiScores()
)
