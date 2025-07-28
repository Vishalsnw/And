
package com.example.goalguru.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goalguru.ui.screens.dashboard.DashboardScreen
import com.example.goalguru.ui.screens.create.CreateGoalScreen
import com.example.goalguru.ui.screens.settings.SettingsScreen
import com.example.goalguru.ui.screens.onboarding.OnboardingScreen
import com.example.goalguru.ui.screens.goal.GoalDetailScreen

@Composable
fun GoalGuruNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {
        composable("dashboard") {
            DashboardScreen(
                onCreateGoal = {
                    navController.navigate("create_goal")
                },
                onSettings = {
                    navController.navigate("settings")
                },
                onGoalClick = { goalId ->
                    navController.navigate("goal_detail/$goalId")
                }
            )
        }

        composable("create_goal") {
            CreateGoalScreen(
                onGoalCreated = {
                    navController.popBackStack()
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable("goal_detail/{goalId}") { backStackEntry ->
            val goalId = backStackEntry.arguments?.getString("goalId") ?: ""
            GoalDetailScreen(
                goalId = goalId,
                onBackPressed = {
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

        composable("onboarding") {
            OnboardingScreen(
                onComplete = {
                    navController.navigate("dashboard") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
    }
}
