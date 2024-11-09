package com.example.level3example.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.level3example.R

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.level3example.viewmodel.RemindersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersListScreen(
    navController: NavHostController,
    viewModel: RemindersViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )

        },
        content = { innerPadding -> ScreenContent(Modifier.padding(innerPadding), viewModel.readReminders()) },
        floatingActionButton = { RemindersListScreenFab(navController) }
    )
}

@Composable
private fun ScreenContent(modifier: Modifier, reminders: List<String>) {
    Column(
        modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Headers()
        RemindersList(reminders)
    }
}

@Composable
private fun RemindersList(reminders: List<String>) {

    LazyColumn {
        items(items = reminders, itemContent = { item ->
            ReminderCard(item)
        })
    }
}

@Composable
private fun ReminderCard(item: String) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp),
    ) {
        Text(
            text = item,
            Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun Headers() {
    Text(
        text = stringResource(id = R.string.reminders),
        modifier = Modifier.padding(bottom = 8.dp),
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = stringResource(id = R.string.see_below),
        modifier = Modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
private fun RemindersListScreenFab(navController: NavHostController) {
    FloatingActionButton(onClick = {
        navController.navigate(RemindersScreens.NewReminder.name)
    }) {
        Text(text = "NEW")
    }
}