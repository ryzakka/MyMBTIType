// File: app/src/main/java/com/dhirekhaf/mytype/MainActivity.kt

package com.dhirekhaf.mytype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dhirekhaf.mytype.data.UserData
import com.dhirekhaf.mytype.data.UserDataRepository
import com.dhirekhaf.mytype.ui.theme.MyTypeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTypeTheme {
                SharedTransitionLayout {
                    // Rute awal sekarang ke "welcome"
                    AppNavigation(
                        sharedTransitionScope = this,
                        startDestination = "welcome"
                    )
                }
            }
        }
    }
}

/**
 * Composable ini berfungsi sebagai gerbang logika tanpa UI untuk
 * memeriksa apakah pengguna baru atau bukan.
 */
@Composable
fun SplashScreenGate(onNavigate: (route: String, popUpTo: String) -> Unit) {
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by meViewModel.userData.collectAsState()

    // LaunchedEffect akan berjalan sekali untuk memeriksa data
    LaunchedEffect(userData) {
        // Pastikan kita tidak membuat keputusan berdasarkan nilai awal yang belum dimuat
        if (userData.isDataLoaded) {
            // [LOGIKA BARU] Periksa apakah nama pengguna kosong.
            if (userData.name.isEmpty()) {
                // Jika kosong, paksa ke halaman edit profil.
                onNavigate("me_edit", "splash_gate")
            } else {
                // Jika sudah ada, lanjutkan ke beranda.
                onNavigate("beranda", "splash_gate")
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(
    sharedTransitionScope: SharedTransitionScope,
    startDestination: String
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Fungsi navigasi umum untuk Bottom Nav Bar
    val onNavigate: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = "welcome") {
            WelcomeScreen(
                onGetInTouchClick = {
                    // Setelah sidik jari diketuk, pergi ke gerbang pemeriksaan
                    navController.navigate("splash_gate") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

        // [RUTE BARU] Gerbang pemeriksaan data pengguna
        composable(route = "splash_gate") {
            SplashScreenGate { destination, popUpToRoute ->
                navController.navigate(destination) {
                    popUpTo(popUpToRoute) { inclusive = true }
                }
            }
        }

        composable(route = "beranda") {
            BerandaScreen(
                navController = navController,
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }

        composable(route = "personalities") {
            with(sharedTransitionScope) {
                PersonalitiesScreen(
                    animatedVisibilityScope = this@composable,
                    navController = navController,
                    currentRoute = currentRoute,
                    onNavigate = onNavigate
                )
            }
        }

        composable(
            route = "personality_detail/{typeId}/{themeColorHex}",
            arguments = listOf(
                navArgument("typeId") { type = NavType.StringType },
                navArgument("themeColorHex") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val typeId = backStackEntry.arguments?.getString("typeId")
            val themeColorHex = backStackEntry.arguments?.getString("themeColorHex")

            if (typeId != null && themeColorHex != null) {
                with(sharedTransitionScope) {
                    PersonalityDetailScreen(
                        animatedVisibilityScope = this@composable,
                        typeId = typeId,
                        navController = navController,
                        themeColorHex = themeColorHex
                    )
                }
            }
        }

        composable(route = "me") {
            MeScreen(
                navController = navController,
                currentRoute = currentRoute,
                onNavigate = onNavigate,
                startInEditMode = false
            )
        }

        // [RUTE PENTING] Halaman edit profil, sekarang bisa diakses dari gerbang
        composable(route = "me_edit") {
            MeScreen(
                navController = navController,
                currentRoute = "me", // Tetap 'me' agar bottom nav tidak tampil
                onNavigate = onNavigate,
                startInEditMode = true // Langsung masuk mode edit
            )
        }

        composable(route = "settings") {
            SettingsScreen(
                navController = navController,
                onNavigateToEdit = {
                    navController.navigate("me_edit")
                }
            )
        }

        composable(route = "personality_test") {
            PersonalityTestScreen(
                navController = navController,
                // Setelah tes selesai, arahkan ke profil ("me")
                onTestComplete = {
                    navController.navigate("me") {
                        // Hapus riwayat navigasi sampai ke beranda agar tidak bisa kembali ke tes
                        popUpTo("beranda")
                    }
                }
            )
        }
    }
}
