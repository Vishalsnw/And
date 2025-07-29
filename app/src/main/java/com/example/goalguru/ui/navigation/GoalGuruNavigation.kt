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
                onNavigateToGoalDetail = { goalId ->
                    navController.navigate("goal_detail/$goalId")
                },
                onNavigateToCreateGoal = {
                    navController.navigate("create_goal")
                }
            )
        }
        
        composable("create_goal") {
            CreateGoalScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            "goal_detail/{goalId}",
            arguments = listOf(navArgument("goalId") { type = NavType.StringType })
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getString("goalId") ?: ""
            GoalDetailScreen(
                goalId = goalId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
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
    }
}
