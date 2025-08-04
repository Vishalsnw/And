package com.example.goalguru.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goalguru.ui.screens.dashboard.DashboardScreen
import com.example.goalguru.ui.screens.goal.CreateGoalScreen
import com.example.goalguru.ui.screens.goal.GoalDetailScreen
import com.example.goalguru.ui.screens.onboarding.OnboardingScreen
import com.example.goalguru.ui.screens.settings.SettingsScreen

@Composable
fun GoalGuruNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "dashboard"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onGetStarted = {
                    navController.navigate("dashboard") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                onNavigateToCreateGoal = {
                    navController.navigate("create_goal")
                },
                onNavigateToGoalDetail = { goalId ->
                    navController.navigate("goal_detail/$goalId")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )
        }

        composable("create_goal") {
            CreateGoalScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onGoalCreated = {
                    navController.popBackStack()
                }
            )
        }

        composable("goal_detail/{goalId}") { backStackEntry ->
            val goalId = backStackEntry.arguments?.getString("goalId") ?: ""
            GoalDetailScreen(
                goalId = goalId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}