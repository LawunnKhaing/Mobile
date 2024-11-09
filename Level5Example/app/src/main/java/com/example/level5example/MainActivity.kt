package com.example.level5example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level5example.ui.theme.screens.NumberScreens
import com.example.level5example.ui.theme.screens.NumbersScreen
import com.example.level5example.ui.theme.ui.Level5ExampleTheme
import com.example.level5example.ui.theme.screens.NumbersHistoryScreen
import com.example.level5example.viewmodel.NumbersViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level5ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NumbersNavHost(navController = navController, modifier = Modifier)
                }

            }
        }
    }
}

@Composable
private fun NumbersNavHost(
    navController: NavHostController, modifier: Modifier
) {
    val viewModel: NumbersViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = NumberScreens.NumbersScreen.name,
        modifier = modifier
    ) {
        composable(route = NumberScreens.NumbersScreen.name) {
            NumbersScreen(navController, viewModel)
        }
        composable(NumberScreens.NumbersHistoryScreen.name) {
            NumbersHistoryScreen(navController, viewModel)
        }
    }
}
