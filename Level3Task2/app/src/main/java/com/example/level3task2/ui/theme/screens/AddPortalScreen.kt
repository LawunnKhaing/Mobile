package com.example.level3task2.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.level3task2.R
import com.example.level3task2.viewmodel.PortalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPortalScreen(navController: NavHostController, viewModel: PortalViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.add_portal))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        AddPortalScreenContent(
            Modifier.padding(paddingValues),
            navController,
            viewModel
        )
    }
}

@Composable
fun AddPortalScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: PortalViewModel
) {
    val nameState = remember { mutableStateOf("") }
    val urlState = remember { mutableStateOf("https://") }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text(text = stringResource(id = R.string.new_portal_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = urlState.value,
            onValueChange = { urlState.value = it },
            label = { Text(text = stringResource(id = R.string.new_portal_url)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (nameState.value.isNotEmpty() && urlState.value.isNotEmpty()) {
                viewModel.addPortal(nameState.value, urlState.value)
                navController.navigate(PortalScreens.ShowPortalScreen.name)
            }
        }) {
            Text(text = stringResource(id = R.string.save_portal))
        }
    }
}
