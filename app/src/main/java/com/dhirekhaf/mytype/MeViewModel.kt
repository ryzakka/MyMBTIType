// Lokasi: app/src/main/java/com/dhirekhaf/mytype/MeViewModel.kt
// [VERSI FINAL - DENGAN FUNGSI LABEL FAVORIT]

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
        initialValue = UserData(
            name = "",
            email = "",
            imageUri = null,
            dimensionScores = emptyMap(),
            hasCompletedTest = false,
            mbtiType = "",
            bio = "",
            hobbies = emptySet(),
            favoriteRelations = emptySet(),
            favoriteTypes = emptyMap(), // Tipe data diubah menjadi Map kosong
            isDataLoaded = false
        )
    )

    fun saveUserProfile(
        name: String,
        email: String,
        bio: String,
        hobbies: List<String>,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            userDataRepository.saveUserProfile(name, email, bio, hobbies, imageUri)
        }
    }

    fun saveMbtiResult(mbtiType: String, scores: Map<Char, Int>) {
        viewModelScope.launch {
            userDataRepository.saveMbtiResult(mbtiType, scores)
        }
    }

    fun resetMbtiTest() {
        viewModelScope.launch {
            userDataRepository.resetMbtiTest()
        }
    }

    fun toggleFavoriteRelation(type1: String, type2: String) {
        val key = listOf(type1, type2).sorted().joinToString("-")
        viewModelScope.launch {
            userDataRepository.toggleFavoriteRelation(key)
        }
    }

    fun addOrUpdateFavorite(typeName: String, label: String) {
        viewModelScope.launch {
            userDataRepository.addOrUpdateFavoriteType(typeName, label)
        }
    }

    fun removeFavorite(typeName: String) {
        viewModelScope.launch {
            userDataRepository.removeFavoriteType(typeName)
        }
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
