package com.example.level4task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level4task2.data.AppDatabase
import com.example.level4task2.ui.theme.screens.HistoryScreen
import com.example.level4task2.ui.theme.screens.PlayScreen
import com.example.level4task2.ui.theme.screens.RPSScreens
import com.example.level4task2.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)
        gameViewModel = GameViewModel(database.gameDao())

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { NavigationBar(navController) }
            ) { innerPadding ->
                NavHost(navController, startDestination = RPSScreens.Play.route, Modifier.padding(innerPadding)) {
                    composable(RPSScreens.Play.route) { PlayScreen(gameViewModel) }
                    composable(RPSScreens.History.route) { HistoryScreen(gameViewModel) }
                }
            }
        }
    }
}

@Composable
fun NavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        RPSScreens.values.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(screen.painterId), contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = false, // Implement logic to determine if selected
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}
