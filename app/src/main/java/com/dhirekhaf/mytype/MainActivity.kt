// File: app/src/main/java/com/dhirekhaf/mytype/MainActivity.kt

package com.dhirekhaf.mytype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                // [PERUBAHAN 1] Arahkan langsung ke "beranda"
                onGetInTouchClick = {
                    navController.navigate("beranda") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

        // [PERUBAHAN 2] Hapus composable untuk "hi_there" karena sudah tidak digunakan
        // composable(route = "hi_there") { ... }

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
                onNavigateToEdit = {
                    navController.navigate("me_edit")
                }
            )
        }

        composable(route = "personality_test") {
            PersonalityTestScreen(navController = navController)
        }
    }
}
