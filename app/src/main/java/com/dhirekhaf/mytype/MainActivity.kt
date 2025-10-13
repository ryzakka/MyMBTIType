// File: app/src/main/java/com/dhirekhaf/mytype/MainActivity.kt
// [MODIFIKASI] Menambahkan rute navigasi untuk FavoritesScreen

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
import com.dhirekhaf.mytype.data.UserDataRepository
import com.dhirekhaf.mytype.ui.theme.MyTypeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTypeTheme {
                SharedTransitionLayout {
                    AppNavigation(
                        sharedTransitionScope = this,
                        startDestination = "welcome"
                    )
                }
            }
        }
    }
}

@Composable
fun SplashScreenGate(onNavigate: (route: String, popUpTo: String) -> Unit) {
    val context = LocalContext.current
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(context)))
    val userData by meViewModel.userData.collectAsState()

    LaunchedEffect(userData) {
        if (userData.isDataLoaded) {
            if (userData.name.isEmpty()) {
                onNavigate("me_edit", "splash_gate")
            } else {
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
                    navController.navigate("splash_gate") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

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
            route = "share_preview"
        ) {
            SharePreviewScreen(navController = navController)
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

        composable(route = "favorites") {
            FavoritesScreen(
                navController = navController,
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }

        composable(route = "me_edit") {
            MeScreen(
                navController = navController,
                currentRoute = "me",
                onNavigate = onNavigate,
                startInEditMode = true
            )
        }

        composable(route = "settings") {
            SettingsScreen(
                navController = navController,
                onNavigateToEdit = { navController.navigate("me_edit")},
                onNavigateToAbout = { navController.navigate("about_screen")}
            )
        }

        composable(route = "about_screen") {
            AboutScreen(navController = navController)
        }

        composable(route = "personality_test") {
            PersonalityTestScreen(
                navController = navController,
                onTestComplete = {
                    navController.navigate("me") {
                        popUpTo("beranda")
                    }
                }
            )
        }

        composable(
            route = "relationship_detail/{type1}/{type2}",
            arguments = listOf(
                navArgument("type1") { type = NavType.StringType },
                navArgument("type2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val type1 = backStackEntry.arguments?.getString("type1")
            val type2 = backStackEntry.arguments?.getString("type2")

            if (type1 != null && type2 != null) {
                RelationshipDetailScreen(
                    type1 = type1,
                    type2 = type2,
                    navController = navController
                )
            }
        }
    }
}
