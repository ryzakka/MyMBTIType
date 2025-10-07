package com.dhirekhaf.mytype

import com.google.gson.annotations.SerializedName

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

data class QuestionOption(
    @SerializedName("option_id")
    val option_id: String,

    @SerializedName("option_text")
    val option_text: String,

    @SerializedName("dimension")
    val dimension: String,

    @SerializedName("direction")
    val direction: Int
)
