package com.example.goalguru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.goalguru.ui.navigation.GoalGuruNavigation
import com.example.goalguru.ui.theme.GoalGuruTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Show splash screen while app loads
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Enable edge-to-edge layout
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GoalGuruApp()
        }
    }
}

@Composable
fun GoalGuruApp() {
    val darkTheme = isSystemInDarkTheme()
    val systemUiController = rememberSystemUiController()

    // Optional loading state
    var isReady by remember { mutableStateOf(false) }

    // Transparent system bars
    DisposableEffect(systemUiController, darkTheme) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
        onDispose {}
    }

    GoalGuruTheme(darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GoalGuruNavigation(
                onDataLoaded = { isReady = true }
            )

            // Optional: show loading UI while data is not ready
            // if (!isReady) {
            //     LoadingScreen()
            // }
        }
    }
}
