// Lokasi: app/src/main/java/com/dhirekhaf/mytype/data/UserDataRepository.kt
// [VERSI FINAL - DENGAN LOGIKA LABEL FAVORIT]

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
        // Kunci ini akan menyimpan data dalam format: "INFP:Teman", "ENTJ:Partner"
        val FAVORITE_TYPES = stringSetPreferencesKey("favorite_types_with_labels")
        val SCORE_I = intPreferencesKey("score_i")
        val SCORE_E = intPreferencesKey("score_e")
        val SCORE_N = intPreferencesKey("score_n")
        val SCORE_S = intPreferencesKey("score_s")
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

            // Mengubah Set<String> dari DataStore menjadi Map<String, String>
            val favoriteTypesSet = preferences[PreferencesKeys.FAVORITE_TYPES] ?: emptySet()
            val favoriteTypesMap = favoriteTypesSet.mapNotNull { entry ->
                val parts = entry.split(":", limit = 2)
                if (parts.size == 2) parts[0] to parts[1] else null
            }.toMap()

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
                favoriteTypes = favoriteTypesMap,
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
            scores.forEach { (key, value) ->
                when (key) {
                    'I' -> preferences[PreferencesKeys.SCORE_I] = value
                    'E' -> preferences[PreferencesKeys.SCORE_E] = value
                    'N' -> preferences[PreferencesKeys.SCORE_N] = value
                    'S' -> preferences[PreferencesKeys.SCORE_S] = value
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
            preferences[PreferencesKeys.HAS_COMPLETED_TEST] = false
            preferences.remove(PreferencesKeys.SCORE_I)
            // ... (hapus semua skor)
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

    suspend fun addOrUpdateFavoriteType(typeName: String, label: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_TYPES]?.toMutableSet() ?: mutableSetOf()
            // Hapus entri lama untuk tipe ini jika ada
            currentFavorites.removeAll { it.startsWith("$typeName:") }
            // Tambahkan entri baru dengan label
            currentFavorites.add("$typeName:$label")
            preferences[PreferencesKeys.FAVORITE_TYPES] = currentFavorites
        }
    }

    suspend fun removeFavoriteType(typeName: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_TYPES]?.toMutableSet() ?: mutableSetOf()
            // Hapus semua entri yang cocok dengan typeName
            currentFavorites.removeAll { it.startsWith("$typeName:") }
            preferences[PreferencesKeys.FAVORITE_TYPES] = currentFavorites
        }
    }
}
