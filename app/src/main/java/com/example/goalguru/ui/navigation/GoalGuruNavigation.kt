
package com.example.goalguru.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goalguru.ui.screens.dashboard.DashboardScreen
import com.example.goalguru.ui.screens.onboarding.OnboardingScreen
import com.example.goalguru.ui.screens.goal.CreateGoalScreen
import com.example.goalguru.ui.screens.settings.SettingsScreen

@Composable
fun GoalGuruNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onOnboardingComplete = {
                    navController.navigate("dashboard") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        
        composable("dashboard") {
            DashboardScreen(
                onCreateGoal = { navController.navigate("create_goal") },
                onSettings = { navController.navigate("settings") }
            )
        }
        
        composable("create_goal") {
            CreateGoalScreen(
                onGoalCreated = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
package com.example.goalguru.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goalguru.ui.screens.dashboard.DashboardScreen
import com.example.goalguru.ui.screens.goal.CreateGoalScreen
import com.example.goalguru.ui.screens.onboarding.OnboardingScreen
import com.example.goalguru.ui.screens.settings.SettingsScreen

@Composable
fun GoalGuruNavigation() {
    val navController = rememberNavController()
    var showOnboarding by remember { mutableStateOf(true) }

    if (showOnboarding) {
        OnboardingScreen(
            onOnboardingComplete = { showOnboarding = false }
        )
    } else {
        NavHost(
            navController = navController,
            startDestination = "dashboard"
        ) {
            composable("dashboard") {
                DashboardScreen(
                    onNavigateToCreateGoal = { navController.navigate("create_goal") },
                    onNavigateToSettings = { navController.navigate("settings") }
                )
            }
            composable("create_goal") {
                CreateGoalScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("settings") {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
