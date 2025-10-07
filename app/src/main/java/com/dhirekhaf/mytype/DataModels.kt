// Lokasi: app/src/main/java/com/dhirekhaf/mytype/DataModels.kt
// [PERBAIKAN - Tambahkan properti hexColor]

package com.dhirekhaf.mytype

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

// --- DEFINISI STRUKTUR DATA (MODEL) ---

data class PersonalityInfo(
    val typeName: String,
    val title: String,
    @DrawableRes val cardImageRes: Int
)

data class PersonalityGroup(
    val groupTitle: String,
    val briefDescription: String,
    @DrawableRes val groupHeaderImageRes: Int,
    @DrawableRes val groupBackgroundImageRes: Int,
    val types: List<PersonalityInfo>,
    val idealPartner: String?,
    val goodPartner: String?
)

data class OtherTypeView(
    val typeName: String,
    val view: String
)

data class RelationshipDetail(
    val otherTypeName: String,
    val strengths: List<String>,
    val challenges: List<String>,
    val advice: List<String>
)

data class PersonalityDetails(
    val typeName: String,
    val description: String,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val careerPaths: List<String>,
    val relationships: String,
    val developmentTips: List<String>,
    @DrawableRes val detailImages: List<Int>,
    val viewsFromOthers: List<OtherTypeView>,
    val relationshipDetails: List<RelationshipDetail>
)

/**
 * Data class untuk tema warna grup (Versi yang lebih sederhana).
 * [PERBAIKAN] Tambahkan properti hexColor untuk pengiriman data yang aman.
 */
data class GroupTheme(
    val primaryColor: Color,
    val secondaryColor: Color,
    val hexColor: String // String heksadesimal yang aman untuk dikirim
)

data class MbtiTheme(
    val primaryColor: Color,
    val secondaryColor: Color
)

data class Question(
    @SerializedName("id") val id: String,
    @SerializedName("question") val question_text: String,
    @SerializedName("explanation") val explanation: String?,
    @SerializedName("options") val options: List<QuestionOption>
)

data class QuestionOption(
    @SerializedName("option_id") val option_id: String,
    @SerializedName("option_text") val option_text: String,
    @SerializedName("dimension") val dimension: String,
    @SerializedName("direction") val direction: Int
)
