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
        // [PERUBAHAN] Nilai awal sekarang mencakup semua properti
        initialValue = UserData("", "", null, emptyMap(), false, "", "", emptyList())
    )

    // [PERUBAHAN] Fungsi saveNameAndImage diganti dengan saveUserProfile
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
}

// Factory tidak perlu diubah
class MeViewModelFactory(private val userDataRepository: UserDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeViewModel(userDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
