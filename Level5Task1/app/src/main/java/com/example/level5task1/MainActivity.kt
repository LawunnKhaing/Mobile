package com.example.level5task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.level5task1.ui.theme.Level5Task1Theme
import com.example.level5task1.ui.theme.screens.CatsScreen
import com.example.level5task1.ui.theme.screens.DogsScreen
import com.example.level5task1.ui.theme.screens.PetsScreens
import com.example.level5task1.viewmodel.CatsViewModel
import com.example.level5task1.viewmodel.DogsViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level5Task1Theme {
                Level5Task1App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Level5Task1App() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name), color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue)
            )
        },
        bottomBar = { BottomNav(navController) }
    ) { innerPadding ->
        NavigationHost(navController, innerPadding)
    }
}

@Composable
fun BottomNav(navController: NavController) {
    NavigationBar(Modifier.background(MaterialTheme.colorScheme.secondary)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val screens = listOf(PetsScreens.CatsScreen, PetsScreens.DogsScreen)

        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }},
                icon = { Image(painter = painterResource(id = screen.customIconImage), colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground), contentDescription = null, modifier = Modifier.size(32.dp)) },
                label = { Text(stringResource(screen.labelResourceId), color = MaterialTheme.colorScheme.onBackground) }
            )
        }
    }
}

@Composable
private fun NavigationHost(navController: NavHostController, innerPadding: PaddingValues) {
    val catsViewModel: CatsViewModel = viewModel()
    val dogsViewModel: DogsViewModel = viewModel()

    NavHost(navController, startDestination = PetsScreens.CatsScreen.route, Modifier.padding(innerPadding)) {
        composable(PetsScreens.CatsScreen.route) { CatsScreen(catsViewModel) }
        composable(PetsScreens.DogsScreen.route) { DogsScreen(dogsViewModel) }
    }
}