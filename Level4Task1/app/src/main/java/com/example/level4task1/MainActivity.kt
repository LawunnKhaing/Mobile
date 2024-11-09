package com.example.level4task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level4task1.ui.theme.screens.AddGameScreen
import com.example.level4task1.viewmodel.GameViewModel
import com.example.level4task1.ui.theme.screens.HomeScreen
import com.example.level4task1.ui.theme.screens.Screen
import com.example.level4task1.ui.theme.themes.Level4Task1Theme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        val viewModel: GameViewModel by viewModels()
        setContent {
            Level4Task1Theme {
                val navController = rememberNavController()
                MyAppNavHost(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.AddGameScreen.route) {
            AddGameScreen(navController = navController, viewModel = viewModel)
        }
    }
}

