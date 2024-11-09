package com.example.level3task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level3task1.ui.theme.screens.GameRatingScreens
import com.example.level3task1.ui.theme.screens.RatingScreen
import com.example.level3task1.ui.theme.screens.StartScreen
import com.example.level3task1.ui.theme.screens.SummaryScreen
import com.example.level3task1.ui.theme.themes.Level3Task1Theme
import com.example.level3task1.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level3Task1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()
                    GameNavHost(navController = navController, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun GameNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val viewModel: GameViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = GameRatingScreens.StartScreen.name,
        modifier = modifier
    ){
        composable(route = GameRatingScreens.StartScreen.name){
            StartScreen(navController = navController, viewModel)
        }
        composable(route = GameRatingScreens.RatingScreen.name){
            RatingScreen(navController = navController, viewModel)
        }
        composable(route = GameRatingScreens.SummaryScreen.name){
            SummaryScreen(navController = navController, viewModel)

        }
    }
}