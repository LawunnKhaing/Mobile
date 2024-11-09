package com.example.level3example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level3example.screens.RemindersScreens
import com.example.level3example.screens.RemindersListScreen
import com.example.level3example.screens.AddReminderScreen
import com.example.level3example.ui.theme.Level3ExampleTheme
import com.example.level3example.viewmodel.RemindersViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level3ExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()
                    RemindersNavHost(navController = navController, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun RemindersNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel: RemindersViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = RemindersScreens.RemindersList.name,
        modifier = modifier
    ) {
        composable(route = RemindersScreens.RemindersList.name) {
            RemindersListScreen(navController, viewModel)
        }
        composable(route = RemindersScreens.NewReminder.name) {
            AddReminderScreen(navController, viewModel)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Level3ExampleTheme {
    }
}
