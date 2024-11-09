package com.example.level5task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level5task2.ui.theme.theme.Level5Task2Theme
import com.example.level5task2.ui.theme.screen.MovieDetailScreen
import com.example.level5task2.ui.theme.screen.MovieScreen
import com.example.level5task2.ui.theme.screen.MovieScreens
import com.example.level5task2.viewmodel.MovieViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            Level5Task2Theme {
                MovieSearchApp()
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchApp(){
    val navController = rememberNavController()
    val movieViewModel : MovieViewModel = viewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.DarkGray)
            )
        },
        content = { innerPadding ->
            MovieNavHost(navController = navController, viewModel = movieViewModel, modifier = Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun MovieNavHost(
    navController: NavHostController,
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController, startDestination = MovieScreens.MovieScreen.route) {
        composable(MovieScreens.MovieScreen.route) {
            MovieScreen(navController, viewModel)
        }
        composable(MovieScreens.MovieDetailScreen.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            movieId?.let {
                MovieDetailScreen(navController, viewModel, it)
            }
        }
    }
}