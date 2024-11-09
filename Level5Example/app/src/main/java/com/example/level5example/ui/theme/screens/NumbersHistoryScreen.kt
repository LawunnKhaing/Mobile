package com.example.level5example.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.level5example.R
import com.example.level5example.data.api.util.Resource
import com.example.level5example.viewmodel.NumbersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumbersHistoryScreen(nc: NavController, vm: NumbersViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(
                        onClick =
                        {
                            vm.deleteHistory()
                            nc.popBackStack()
                        }
                    )
                    {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete history",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { nc.popBackStack() },
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            "backIcon",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            ScreenContent(
                Modifier.padding(innerPadding),
                vm
            )
        },
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    vm: NumbersViewModel,
) {
    val context = LocalContext.current

    // The Livedata object in viewmodel must be subscribed to directly.
    val historyResource: Resource<List<String>>? by vm.numbersFromFirestoreResource.observeAsState()

    val resultText = when (historyResource) {
        is Resource.Success -> stringResource(id = R.string.success)
        is Resource.Error -> historyResource!!.message.toString()
        else -> String() //stringResource(R.string.something_wrong_state)
    }
    if (!(resultText.equals(String())||resultText.equals(stringResource(id = R.string.success))))
        Toast.makeText(context, resultText, Toast.LENGTH_SHORT).show()

    Column(
        modifier.padding(16.dp)
    )
    {
        if (resultText.equals(stringResource(id = R.string.success)))
            LazyColumn {
                items(items = historyResource?.data!!,
                    itemContent = { item ->
                        NumberCard(item)
                    })
            }
    }
}


@Composable
private fun NumberCard(item: String) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Text(
            text = item,
            Modifier.padding(16.dp)
        )
    }
}

