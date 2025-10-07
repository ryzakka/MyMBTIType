// File: app/src/main/java/com/dhirekhaf/mytype/data/UserDataRepository.kt

package com.dhirekhaf.mytype.data

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserDataRepository(context: Context) {

    private val dataStore = context.dataStore

    // Definisikan semua kunci di sini
    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val MBTI_TYPE = stringPreferencesKey("mbti_type")
        val IMAGE_URI = stringPreferencesKey("image_uri")
        val SCORE_MAP = stringPreferencesKey("score_map") // Ganti dari skor individu menjadi satu map

        // [PERUBAHAN] Kunci baru untuk data baru
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_BIO = stringPreferencesKey("user_bio")
        val USER_HOBBIES = stringSetPreferencesKey("user_hobbies") // Gunakan stringSet untuk List
    }

    // Membaca semua data dari DataStore
    val userData: Flow<UserData> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val scoresJson = preferences[PreferencesKeys.SCORE_MAP] ?: "{}"
            val scores: Map<Char, Int> = Gson().fromJson(scoresJson, object : TypeToken<Map<Char, Int>>() {}.type)

            UserData(
                name = preferences[PreferencesKeys.USER_NAME] ?: "",
                mbtiType = preferences[PreferencesKeys.MBTI_TYPE] ?: "",
                imageUri = preferences[PreferencesKeys.IMAGE_URI]?.let { Uri.parse(it) },
                dimensionScores = scores,
                isDataLoaded = true,

                // [PERUBAHAN] Muat data baru
                email = preferences[PreferencesKeys.USER_EMAIL] ?: "",
                bio = preferences[PreferencesKeys.USER_BIO] ?: "",
                hobbies = preferences[PreferencesKeys.USER_HOBBIES]?.toList() ?: emptyList()
            )
        }

    // [PERUBAHAN] Fungsi simpan sekarang mencakup semua data profil
    suspend fun saveUserProfile(
        name: String,
        email: String,
        bio: String,
        hobbies: List<String>,
        imageUri: Uri?
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.USER_EMAIL] = email
            preferences[PreferencesKeys.USER_BIO] = bio
            preferences[PreferencesKeys.USER_HOBBIES] = hobbies.toSet()
            imageUri?.let { preferences[PreferencesKeys.IMAGE_URI] = it.toString() }
        }
    }

    suspend fun saveMbtiResult(result: String, scores: Map<Char, Int>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MBTI_TYPE] = result
            preferences[PreferencesKeys.SCORE_MAP] = Gson().toJson(scores)
        }
    }

    suspend fun resetMbtiTest() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MBTI_TYPE] = ""
            preferences.remove(PreferencesKeys.SCORE_MAP)
        }
    }
}
