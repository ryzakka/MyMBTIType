// Lokasi: app/src/main/java/com/dhirekhaf/mytype/data/UserDataRepository.kt

package com.dhirekhaf.mytype.data

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

// Inisialisasi DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

class UserDataRepository(context: Context) {

    private val dataStore = context.dataStore

    // Definisikan semua kunci (keys) untuk DataStore
    private object PreferencesKeys {
        val NAME = stringPreferencesKey("user_name")
        val EMAIL = stringPreferencesKey("user_email")
        val BIO = stringPreferencesKey("user_bio")
        val IMAGE_URI = stringPreferencesKey("user_image_uri")
        val HAS_COMPLETED_TEST = booleanPreferencesKey("has_completed_test")
        val MBTI_TYPE = stringPreferencesKey("mbti_type")
        val HOBBIES = stringSetPreferencesKey("user_hobbies")
        val FAVORITE_RELATIONS = stringSetPreferencesKey("favorite_relations")
        val SCORE_I = intPreferencesKey("score_i")
        val SCORE_E = intPreferencesKey("score_e")
        val SCORE_N = intPreferencesKey("score_n")
        val SCORE_S = intPreferencesKey("score_s")
        val SCORE_T = intPreferencesKey("score_t")
        val SCORE_F = intPreferencesKey("score_f")
        val SCORE_J = intPreferencesKey("score_j")
        val SCORE_P = intPreferencesKey("score_p")
    }

    // --- PERBAIKAN UTAMA ADA DI SINI ---
    val userData: Flow<UserData> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val scores = mapOf(
                'I' to (preferences[PreferencesKeys.SCORE_I] ?: 0),
                'E' to (preferences[PreferencesKeys.SCORE_E] ?: 0),
                'N' to (preferences[PreferencesKeys.SCORE_N] ?: 0),
                'S' to (preferences[PreferencesKeys.SCORE_S] ?: 0),
                'T' to (preferences[PreferencesKeys.SCORE_T] ?: 0),
                'F' to (preferences[PreferencesKeys.SCORE_F] ?: 0),
                'J' to (preferences[PreferencesKeys.SCORE_J] ?: 0),
                'P' to (preferences[PreferencesKeys.SCORE_P] ?: 0)
            )

            UserData(
                name = preferences[PreferencesKeys.NAME] ?: "",
                email = preferences[PreferencesKeys.EMAIL] ?: "",
                imageUri = preferences[PreferencesKeys.IMAGE_URI],
                dimensionScores = scores,
                hasCompletedTest = preferences[PreferencesKeys.HAS_COMPLETED_TEST] ?: false,
                mbtiType = preferences[PreferencesKeys.MBTI_TYPE] ?: "",
                bio = preferences[PreferencesKeys.BIO] ?: "",
                hobbies = preferences[PreferencesKeys.HOBBIES] ?: emptySet(),
                favoriteRelations = preferences[PreferencesKeys.FAVORITE_RELATIONS] ?: emptySet(),
                // --- PERBAIKAN ---
                // Setelah data berhasil dibaca dari DataStore, set isDataLoaded menjadi true.
                // Ini akan memberi sinyal ke SplashScreenGate untuk bernavigasi.
                isDataLoaded = true
            )
        }

    suspend fun saveUserProfile(name: String, email: String, bio: String, hobbies: List<String>, imageUri: Uri?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NAME] = name
            preferences[PreferencesKeys.EMAIL] = email
            preferences[PreferencesKeys.BIO] = bio
            preferences[PreferencesKeys.HOBBIES] = hobbies.toSet()
            if (imageUri != null) {
                preferences[PreferencesKeys.IMAGE_URI] = imageUri.toString()
            } else {
                preferences.remove(PreferencesKeys.IMAGE_URI)
            }
        }
    }

    suspend fun saveMbtiResult(mbtiType: String, scores: Map<Char, Int>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MBTI_TYPE] = mbtiType
            preferences[PreferencesKeys.HAS_COMPLETED_TEST] = true
            preferences[PreferencesKeys.SCORE_I] = scores['I'] ?: 0
            preferences[PreferencesKeys.SCORE_E] = scores['E'] ?: 0
            preferences[PreferencesKeys.SCORE_N] = scores['N'] ?: 0
            preferences[PreferencesKeys.SCORE_S] = scores['S'] ?: 0
            preferences[PreferencesKeys.SCORE_T] = scores['T'] ?: 0
            preferences[PreferencesKeys.SCORE_F] = scores['F'] ?: 0
            preferences[PreferencesKeys.SCORE_J] = scores['J'] ?: 0
            preferences[PreferencesKeys.SCORE_P] = scores['P'] ?: 0
        }
    }

    suspend fun resetMbtiTest() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MBTI_TYPE] = ""
            preferences[PreferencesKeys.HAS_COMPLETED_TEST] = false
            preferences.remove(PreferencesKeys.SCORE_I)
            preferences.remove(PreferencesKeys.SCORE_E)
            preferences.remove(PreferencesKeys.SCORE_N)
            preferences.remove(PreferencesKeys.SCORE_S)
            preferences.remove(PreferencesKeys.SCORE_T)
            preferences.remove(PreferencesKeys.SCORE_F)
            preferences.remove(PreferencesKeys.SCORE_J)
            preferences.remove(PreferencesKeys.SCORE_P)
        }
    }

    suspend fun toggleFavoriteRelation(key: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RELATIONS]?.toMutableSet() ?: mutableSetOf()
            if (currentFavorites.contains(key)) {
                currentFavorites.remove(key)
            } else {
                currentFavorites.add(key)
            }
            preferences[PreferencesKeys.FAVORITE_RELATIONS] = currentFavorites
        }
    }
}
