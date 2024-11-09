package com.example.level3example.screens

import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.level3example.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.level3example.viewmodel.RemindersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(navController: NavHostController, viewModel: RemindersViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )

        },
        content = { innerPadding ->
            ScreenContent(
                Modifier.padding(innerPadding),
                navController,
                updateReminderList = {
                    viewModel.addReminder(it)
                }

            ) }
    )
}



@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    updateReminderList: (String) -> Unit
) {
    val context = LocalContext.current
    var newReminder by remember { mutableStateOf(String()) }
    Column(
        modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newReminder,
            placeholder = { Text(text = stringResource(id = R.string.new_reminder)) },
            onValueChange = {
                newReminder = it
            },
            label = { Text(stringResource(R.string.enter_new_reminder)) }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {
                val message = if (newReminder.isNotBlank()) {
                    updateReminderList(newReminder)
                    newReminder = String()
                    navController.popBackStack()
                    R.string.reminder_added
                } else {
                    R.string.no_empty_reminder
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }) {
            Text(text = stringResource(R.string.add_reminder))
        }
    }
}

@Composable
private fun AddReminderScreenFab(navController: NavHostController) {
    FloatingActionButton(onClick = {
        navController.popBackStack()
    }) {
        Text(text = "ADD")
    }
}