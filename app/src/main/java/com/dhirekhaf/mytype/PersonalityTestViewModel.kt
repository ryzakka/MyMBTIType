// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestViewModel.kt

package com.dhirekhaf.mytype

import android.app.Application
import androidx.compose.runtime.mutableStateOf
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

    private val finalScores = mutableStateOf<Map<Char, Int>?>(null)

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
        val scores = mutableMapOf(
            'E' to 0, 'I' to 0, 'S' to 0, 'N' to 0,
            'T' to 0, 'F' to 0, 'J' to 0, 'P' to 0
        )

        // --- [PERBAIKAN FINAL] Menggunakan logika perhitungan ANDA dengan benar ---
        _uiState.value.userAnswers.forEach { (questionId, selectedOptionId) ->
            val question = _uiState.value.allQuestions.find { it.id == questionId }
            val selectedOption = question?.options?.find { it.option_id == selectedOptionId }

            if (selectedOption != null) {
                // Implementasi persis seperti yang Anda berikan, menggunakan getOrDefault
                when (selectedOption.dimension) {
                    "EI" -> if (selectedOption.direction > 0) scores['E'] = scores.getOrDefault('E', 0) + 1 else scores['I'] = scores.getOrDefault('I', 0) + 1
                    "SN" -> if (selectedOption.direction > 0) scores['S'] = scores.getOrDefault('S', 0) + 1 else scores['N'] = scores.getOrDefault('N', 0) + 1
                    "TF" -> if (selectedOption.direction > 0) scores['T'] = scores.getOrDefault('T', 0) + 1 else scores['F'] = scores.getOrDefault('F', 0) + 1
                    "JP" -> if (selectedOption.direction > 0) scores['J'] = scores.getOrDefault('J', 0) + 1 else scores['P'] = scores.getOrDefault('P', 0) + 1
                }
            }
        }
        // --- AKHIR PERBAIKAN ---

        val ei = if (scores.getOrDefault('E', 0) >= scores.getOrDefault('I', 0)) 'E' else 'I'
        val sn = if (scores.getOrDefault('S', 0) >= scores.getOrDefault('N', 0)) 'S' else 'N'
        val tf = if (scores.getOrDefault('T', 0) >= scores.getOrDefault('F', 0)) 'T' else 'F'
        val jp = if (scores.getOrDefault('J', 0) >= scores.getOrDefault('P', 0)) 'J' else 'P'
        val mbtiResult = "$ei$sn$tf$jp"

        finalScores.value = scores
        _uiState.update { it.copy(testResult = mbtiResult, isLoading = false) }
    }

    fun getFinalScores(): Map<Char, Int>? {
        return finalScores.value
    }
}
