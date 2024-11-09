package com.example.level3task2

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
import com.example.level3task2.ui.theme.screens.AddPortalScreen
import com.example.level3task2.ui.theme.screens.PortalScreens
import com.example.level3task2.ui.theme.screens.ShowPortalScreen
import com.example.level3task2.ui.theme.themes.Level3Task2Theme
import com.example.level3task2.viewmodel.PortalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level3Task2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    PortalNavHost(navController = navController, modifier = Modifier)

                }
            }
        }
    }
}

@Composable
private fun PortalNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    val viewModel: PortalViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = PortalScreens.ShowPortalScreen.name,
        modifier = modifier
    ){
        composable(route = PortalScreens.ShowPortalScreen.name){
            ShowPortalScreen(navController = navController, viewModel)
        }
        composable(route = PortalScreens.AddPortalScreen.name){
            AddPortalScreen(navController = navController, viewModel)

        }
    }
}