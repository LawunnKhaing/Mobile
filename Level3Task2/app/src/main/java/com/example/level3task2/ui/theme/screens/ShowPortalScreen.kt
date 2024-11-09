package com.example.level3task2.ui.theme.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.level3task2.R
import com.example.level3task2.viewmodel.PortalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowPortalScreen(navController: NavHostController, viewModel: PortalViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.student_portals))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(PortalScreens.AddPortalScreen.name)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(id = R.string.add_portal),
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        ShowPortalScreenContent(Modifier.padding(paddingValues), viewModel)
    }
}

@Composable
fun ShowPortalScreenContent(modifier: Modifier, viewModel: PortalViewModel) {
    val context = LocalContext.current

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(viewModel.portals) { portal ->
            Column(
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(portal.url))
                        context.startActivity(intent)
                    }
                    .padding(16.dp)
            ) {
                Text(text = portal.title, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
                Text(text = portal.url, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
