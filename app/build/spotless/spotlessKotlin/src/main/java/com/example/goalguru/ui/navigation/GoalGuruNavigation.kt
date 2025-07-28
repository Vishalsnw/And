package com.example.goalguru.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.goalguru.ui.screens.create.CreateGoalScreen
import com.example.goalguru.ui.screens.dashboard.DashboardScreen
import com.example.goalguru.ui.screens.goal.GoalDetailScreen
import com.example.goalguru.ui.screens.settings.SettingsScreen

@Composable
fun GoalGuruNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
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
                },
            )
        }

        composable("create_goal") {
            CreateGoalScreen(
                onGoalCreated = {
                    navController.popBackStack()
                },
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }

        composable(
            route = "goal_detail/{goalId}",
            arguments = listOf(
                navArgument("goalId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getString("goalId") ?: ""
            GoalDetailScreen(
                goalId = goalId,
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }

        composable("settings") {
            SettingsScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }
    }
}
