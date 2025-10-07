// File: app/src/main/java/com/dhirekhaf/mytype/DataModels.kt

package com.dhirekhaf.mytype

import com.google.gson.annotations.SerializedName

/**
 * Mewakili satu pertanyaan dalam tes kepribadian.
 * Pastikan nama di @SerializedName cocok persis dengan field di questions.json
 */
data class Question(
    @SerializedName("id")
    val id: String,

    // [PERBAIKAN] Pastikan ini 'question', bukan 'question_text'
    @SerializedName("question")
    val question_text: String,

    @SerializedName("explanation")
    val explanation: String?,

    @SerializedName("options")
    val options: List<QuestionOption>
)

/**
 * Mewakili satu pilihan jawaban.
 * Ini adalah perbaikan paling penting.
 */
data class QuestionOption(
    // [PERBAIKAN KRUSIAL] Nama field di JSON adalah "option_id", bukan "id_option"
    @SerializedName("option_id")
    val option_id: String,

    // [PERBAIKAN KRUSIAL] Nama field di JSON adalah "option_text", bukan "text" atau lainnya
    @SerializedName("option_text")
    val option_text: String,

    @SerializedName("dimension")
    val dimension: String,

    // 'direction' tidak lagi digunakan di logika baru, tapi kita biarkan agar tidak error saat parsing
    @SerializedName("direction")
    val direction: Int
)

