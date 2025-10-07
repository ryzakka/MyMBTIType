// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestViewModel.kt

package com.dhirekhaf.mytype

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
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
    val userAnswers: Map<String, QuestionOption> = emptyMap()
)

class PersonalityTestViewModel(application: Application) : AndroidViewModel(application) {

    // --- PERUBAHAN DI SINI ---
    private val QUESTIONS_PER_GROUP = 8 // Total 32 soal / 4 grup
    // --- AKHIR PERUBAHAN ---

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
                val questions = Gson().fromJson(reader, Array<Question>::class.java).toList()
                // --- PERUBAHAN DI SINI ---
                // Perhitungan total grup sekarang otomatis berdasarkan konstanta
                val totalGroups = (questions.size + QUESTIONS_PER_GROUP - 1) / QUESTIONS_PER_GROUP

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        allQuestions = questions,
                        // Ambil grup pertama sejumlah QUESTIONS_PER_GROUP
                        currentQuestionGroup = questions.take(QUESTIONS_PER_GROUP),
                        totalGroups = totalGroups,
                        progress = 1f / totalGroups, // Progress awal
                        progressText = "1 of $totalGroups"
                    )
                }
                // --- AKHIR PERUBAHAN ---
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Failed to load questions: ${e.message}")
                }
            }
        }
    }

    fun answerQuestion(questionId: String, selectedOption: QuestionOption) {
        _uiState.update { currentState ->
            val newAnswers = currentState.userAnswers.toMutableMap()
            newAnswers[questionId] = selectedOption
            currentState.copy(userAnswers = newAnswers)
        }
    }

    fun nextGroup() {
        // --- PERUBAHAN DI SINI ---
        val currentIndex = _uiState.value.currentGroupIndex
        val questionsPerGroup = QUESTIONS_PER_GROUP // Gunakan konstanta

        if ((currentIndex + 1) * questionsPerGroup >= _uiState.value.allQuestions.size) {
            calculateResult()
        } else {
            val nextIndex = currentIndex + 1
            val nextGroup = _uiState.value.allQuestions.subList(
                nextIndex * questionsPerGroup,
                ((nextIndex + 1) * questionsPerGroup).coerceAtMost(_uiState.value.allQuestions.size)
            )
            _uiState.update {
                it.copy(
                    currentQuestionGroup = nextGroup,
                    currentGroupIndex = nextIndex,
                    progress = (nextIndex + 1).toFloat() / it.totalGroups,
                    progressText = "${nextIndex + 1} of ${it.totalGroups}"
                )
            }
        }
        // --- AKHIR PERUBAHAN ---
    }

    private fun calculateResult() {
        val scores = mutableMapOf(
            'E' to 0, 'I' to 0, 'S' to 0, 'N' to 0,
            'T' to 0, 'F' to 0, 'J' to 0, 'P' to 0
        )

        _uiState.value.userAnswers.values.forEach { option ->
            when (option.dimension) {
                "EI" -> if (option.direction > 0) scores['E'] = scores.getOrDefault('E', 0) + 1 else scores['I'] = scores.getOrDefault('I', 0) + 1
                "SN" -> if (option.direction > 0) scores['S'] = scores.getOrDefault('S', 0) + 1 else scores['N'] = scores.getOrDefault('N', 0) + 1
                "TF" -> if (option.direction > 0) scores['T'] = scores.getOrDefault('T', 0) + 1 else scores['F'] = scores.getOrDefault('F', 0) + 1
                "JP" -> if (option.direction > 0) scores['J'] = scores.getOrDefault('J', 0) + 1 else scores['P'] = scores.getOrDefault('P', 0) + 1
            }
        }

        val ei = if (scores.getOrDefault('E', 0) >= scores.getOrDefault('I', 0)) 'E' else 'I'
        val sn = if (scores.getOrDefault('S', 0) >= scores.getOrDefault('N', 0)) 'S' else 'N'
        val tf = if (scores.getOrDefault('T', 0) >= scores.getOrDefault('F', 0)) 'T' else 'F'
        val jp = if (scores.getOrDefault('J', 0) >= scores.getOrDefault('P', 0)) 'J' else 'P'
        val mbtiResult = "$ei$sn$tf$jp"

        finalScores.value = scores
        _uiState.update { it.copy(testResult = mbtiResult) }
    }

    fun getFinalScores(): Map<Char, Int>? {
        return finalScores.value
    }
}
