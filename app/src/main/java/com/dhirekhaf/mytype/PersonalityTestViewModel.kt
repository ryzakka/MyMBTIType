// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestViewModel.kt

package com.dhirekhaf.mytype

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStreamReader

data class PersonalityTestUiState(
    val isLoading: Boolean = true,
    val currentQuestionGroup: List<Question> = emptyList(),
    val allQuestions: List<Question> = emptyList(),
    val currentGroupIndex: Int = 0,
    val totalGroups: Int = 0,
    val progress: Float = 0f,
    val progressText: String = "",
    val testResult: String? = null,
    val error: String? = null,
    val userAnswers: Map<String, String> = emptyMap()
)

class PersonalityTestViewModel(application: Application) : AndroidViewModel(application) {

    private val QUESTIONS_PER_GROUP = 5

    private val _uiState = MutableStateFlow(PersonalityTestUiState())
    val uiState: StateFlow<PersonalityTestUiState> = _uiState.asStateFlow()
    private var finalScores: Map<Char, Int>? = null

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            try {
                val inputStream = getApplication<Application>().assets.open("questions.json")
                val reader = InputStreamReader(inputStream)
                val questionListType = object : TypeToken<List<Question>>() {}.type
                val questions: List<Question> = Gson().fromJson(reader, questionListType)

                if (questions.isEmpty()) {
                    throw Exception("questions.json is empty or could not be parsed.")
                }

                val totalGroups = (questions.size + QUESTIONS_PER_GROUP - 1) / QUESTIONS_PER_GROUP

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        allQuestions = questions,
                        currentQuestionGroup = questions.take(QUESTIONS_PER_GROUP),
                        totalGroups = totalGroups,
                        progress = 1f / totalGroups,
                        progressText = "1 of $totalGroups"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Failed to load questions: ${e.message}")
                }
            }
        }
    }

    fun answerQuestion(questionId: String, optionId: String) {
        _uiState.update { currentState ->
            val newAnswers = currentState.userAnswers.toMutableMap()
            newAnswers[questionId] = optionId
            currentState.copy(userAnswers = newAnswers)
        }
    }

    fun nextGroup() {
        val currentIndex = _uiState.value.currentGroupIndex
        val totalQuestions = _uiState.value.allQuestions.size

        if ((currentIndex + 1) * QUESTIONS_PER_GROUP >= totalQuestions) {
            calculateResult()
        } else {
            val nextIndex = currentIndex + 1
            val nextGroupStartIndex = nextIndex * QUESTIONS_PER_GROUP
            val nextGroupEndIndex = (nextGroupStartIndex + QUESTIONS_PER_GROUP).coerceAtMost(totalQuestions)
            val nextGroup = _uiState.value.allQuestions.subList(nextGroupStartIndex, nextGroupEndIndex)

            _uiState.update {
                it.copy(
                    currentQuestionGroup = nextGroup,
                    currentGroupIndex = nextIndex,
                    progress = (nextIndex + 1).toFloat() / it.totalGroups,
                    progressText = "${nextIndex + 1} of ${it.totalGroups}"
                )
            }
        }
    }

    private fun calculateResult() {
        val calculatedScores = mutableMapOf<Char, Int>()
        var mbtiResult = ""
        val dimensions = mapOf('E' to "EI", 'S' to "SN", 'T' to "TF", 'J' to "JP")

        dimensions.forEach { (mainTrait, dimString) ->
            val relevantQuestions = _uiState.value.allQuestions.filter { question ->
                question.options.any { it.dimension == dimString }
            }
            val maxScorePerDimension = relevantQuestions.size
            var bipolarScore = 0
            relevantQuestions.forEach { question ->
                val userAnswerId = _uiState.value.userAnswers[question.id]
                val selectedOption = question.options.find { it.option_id == userAnswerId }
                if (selectedOption != null) {
                    bipolarScore += selectedOption.direction
                }
            }

            val finalScore = ((bipolarScore.toFloat() / maxScorePerDimension) * 50 + 50).toInt()
            calculatedScores[mainTrait] = finalScore.coerceIn(0, 100)

            mbtiResult += when (mainTrait) {
                'E' -> if (finalScore >= 50) "E" else "I"
                'S' -> if (finalScore >= 50) "S" else "N"
                'T' -> if (finalScore >= 50) "T" else "F"
                'J' -> if (finalScore >= 50) "J" else "P"
                else -> ""
            }
        }

        finalScores = calculatedScores
        _uiState.update { it.copy(testResult = mbtiResult, isLoading = false) }
    }

    fun getFinalScoresForSaving(): Map<Char, Int>? {
        return finalScores
    }
}
