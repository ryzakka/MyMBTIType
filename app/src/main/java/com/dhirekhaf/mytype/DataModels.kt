// File: app/src/main/java/com/dhirekhaf/mytype/DataModels.kt

package com.dhirekhaf.mytype

import com.google.gson.annotations.SerializedName

/**
 * Mewakili satu pertanyaan dalam tes kepribadian.
 */
data class Question(
    @SerializedName("id")
    val id: String,

    @SerializedName("question")
    val question_text: String,

    @SerializedName("explanation")
    val explanation: String?,

    @SerializedName("options")
    val options: List<QuestionOption>
)

/**
 * Mewakili satu pilihan jawaban.
 */
data class QuestionOption(
    // Field JSON "option_id" dipetakan ke properti 'option_id'
    @SerializedName("option_id") // <--- PERIKSA KEMBALI, INI SUDAH BENAR
    val option_id: String,

    // --- PERBAIKAN KRITIS DAN FINAL DI SINI ---
    // Field JSON "option_text" harus dipetakan ke properti 'option_text'
    @SerializedName("option_text") // <--- INI KESALAHAN FATAL SAYA SEBELUMNYA
    val option_text: String,

    @SerializedName("dimension")
    val dimension: String,

    @SerializedName("direction")
    val direction: Int
)

