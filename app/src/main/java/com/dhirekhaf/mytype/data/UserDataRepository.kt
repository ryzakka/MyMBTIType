// File: app/src/main/java/com/dhirekhaf/mytype/data/UserDataRepository.kt
package com.dhirekhaf.mytype.data

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Buat DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserDataRepository(context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val MBTI_TYPE = stringPreferencesKey("mbti_type")
        val IMAGE_URI = stringPreferencesKey("image_uri")
        // --- TAMBAHKAN PREFERENCES UNTUK SKOR ---
        val SCORE_E = intPreferencesKey("score_e")
        val SCORE_I = intPreferencesKey("score_i")
        val SCORE_S = intPreferencesKey("score_s")
        val SCORE_N = intPreferencesKey("score_n")
        val SCORE_T = intPreferencesKey("score_t")
        val SCORE_F = intPreferencesKey("score_f")
        val SCORE_J = intPreferencesKey("score_j")
        val SCORE_P = intPreferencesKey("score_p")
    }

    val userData: Flow<UserData> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val name = preferences[PreferencesKeys.USER_NAME] ?: ""
            val mbtiType = preferences[PreferencesKeys.MBTI_TYPE] ?: ""
            val imageUriString = preferences[PreferencesKeys.IMAGE_URI]
            val imageUri = imageUriString?.let { Uri.parse(it) }

            // --- BACA SKOR DARI PREFERENCES ---
            val scores = mapOf(
                'E' to (preferences[PreferencesKeys.SCORE_E] ?: 0),
                'I' to (preferences[PreferencesKeys.SCORE_I] ?: 0),
                'S' to (preferences[PreferencesKeys.SCORE_S] ?: 0),
                'N' to (preferences[PreferencesKeys.SCORE_N] ?: 0),
                'T' to (preferences[PreferencesKeys.SCORE_T] ?: 0),
                'F' to (preferences[PreferencesKeys.SCORE_F] ?: 0),
                'J' to (preferences[PreferencesKeys.SCORE_J] ?: 0),
                'P' to (preferences[PreferencesKeys.SCORE_P] ?: 0)
            )

            UserData(name, mbtiType, imageUri, scores)
        }

    suspend fun saveNameAndImage(name: String, imageUri: Uri?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.IMAGE_URI] = imageUri?.toString() ?: ""
        }
    }

    // --- BUAT FUNGSI BARU UNTUK MENYIMPAN HASIL & SKOR ---
    suspend fun saveMbtiResult(result: String, scores: Map<Char, Int>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MBTI_TYPE] = result
            scores.forEach { (key, value) ->
                when(key) {
                    'E' -> preferences[PreferencesKeys.SCORE_E] = value
                    'I' -> preferences[PreferencesKeys.SCORE_I] = value
                    'S' -> preferences[PreferencesKeys.SCORE_S] = value
                    'N' -> preferences[PreferencesKeys.SCORE_N] = value
                    'T' -> preferences[PreferencesKeys.SCORE_T] = value
                    'F' -> preferences[PreferencesKeys.SCORE_F] = value
                    'J' -> preferences[PreferencesKeys.SCORE_J] = value
                    'P' -> preferences[PreferencesKeys.SCORE_P] = value
                }
            }
        }
    }

    suspend fun resetMbtiTest() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MBTI_TYPE] = ""
            // Reset skor juga
            preferences.remove(PreferencesKeys.SCORE_E)
            preferences.remove(PreferencesKeys.SCORE_I)
            preferences.remove(PreferencesKeys.SCORE_S)
            preferences.remove(PreferencesKeys.SCORE_N)
            preferences.remove(PreferencesKeys.SCORE_T)
            preferences.remove(PreferencesKeys.SCORE_F)
            preferences.remove(PreferencesKeys.SCORE_J)
            preferences.remove(PreferencesKeys.SCORE_P)
        }
    }
}
