// File: app/src/main/java/com/dhirekhaf/mytype/MeViewModel.kt

package com.dhirekhaf.mytype

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dhirekhaf.mytype.data.UserData
import com.dhirekhaf.mytype.data.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MeViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {

    val userData: StateFlow<UserData> = userDataRepository.userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserData("", "", null, emptyMap())
    )

    // --- PERBAIKAN DI SINI ---
    fun saveNameAndImage(name: String, imageUri: Uri?) {
        viewModelScope.launch {
            // Panggil satu fungsi yang benar dari repository
            userDataRepository.saveNameAndImage(name, imageUri)
        }
    }
    // --- AKHIR PERBAIKAN ---

    fun saveMbtiResult(mbtiType: String, scores: Map<Char, Int>) {
        viewModelScope.launch {
            userDataRepository.saveMbtiResult(mbtiType, scores)
        }
    }

    fun resetMbtiTest() {
        viewModelScope.launch {
            // Kita gunakan fungsi yang ada di repository
            userDataRepository.resetMbtiTest()
        }
    }

    fun recalculateScores(mbtiType: String): Map<Char, Int> {
        val scores = mutableMapOf<Char, Int>()
        // Jumlah soal per dimensi sekarang 8, karena total 32 soal untuk 4 dimensi
        val questionsPerDimension = 8

        mbtiType.forEach { char ->
            when (char) {
                'E' -> { scores['E'] = questionsPerDimension; scores['I'] = 0 }
                'I' -> { scores['I'] = questionsPerDimension; scores['E'] = 0 }
                'S' -> { scores['S'] = questionsPerDimension; scores['N'] = 0 }
                'N' -> { scores['N'] = questionsPerDimension; scores['S'] = 0 }
                'T' -> { scores['T'] = questionsPerDimension; scores['F'] = 0 }
                'F' -> { scores['F'] = questionsPerDimension; scores['T'] = 0 }
                'J' -> { scores['J'] = questionsPerDimension; scores['P'] = 0 }
                'P' -> { scores['P'] = questionsPerDimension; scores['J'] = 0 }
            }
        }
        return scores
    }
}

class MeViewModelFactory(private val userDataRepository: UserDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeViewModel(userDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
