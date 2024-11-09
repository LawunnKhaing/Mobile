package com.example.level5example.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.level5example.R
import com.example.level5example.data.api.NumbersApi
import com.example.level5example.data.api.util.Resource
import com.example.level5example.viewmodel.NumbersViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumbersScreen(nc: NavController, vm: NumbersViewModel) {
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
                )
            )
        },
        content = { innerPadding ->
            ScreenContent(
                Modifier.padding(innerPadding),
                vm,
                getNewNumber = {
                    vm.getNumber()
                },
                addNumberToFirestore = { //Number ->
                    vm.addNumberToFirestore(it)
                }
            )
        },
        floatingActionButton = { TriviaHistoryFab(nc, vm) }
    )

}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: NumbersViewModel,
    getNewNumber: () -> Unit,
    addNumberToFirestore: (com.example.level5example.data.model.Number) -> Unit // Specify the correct import
) {
    // Make sure to import :
    // import androidx.compose.runtime.getValue
    // import androidx.compose.runtime.livedata.observeAsState

    // The Livedata object in viewmodel must be subscribed to directly.
    val numberResourceState = viewModel.numberResource.observeAsState()
    val numberResource = numberResourceState.value

    /*
       Determine in which state what to show
       You could expand this in the future by for example also adding a loading indicator
     */
    val numberText = when (numberResource) {
        is Resource.Success -> {
            numberResource?.data?.text.toString()
        }

        is Resource.Error -> numberResource?.message.toString()
        is Resource.Loading -> stringResource(R.string.loading_text)
        is Resource.Empty -> stringResource(id = R.string.empty_number_placeholder)
        else -> stringResource(R.string.something_wrong_state)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            // Numbertype constant, but first character uppercase.
            text = NumbersApi.numberType.replaceFirstChar { it.uppercase() },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(vertical = 60.dp, horizontal = 16.dp)
        )

        Text(
            text = numberText,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
                .heightIn(min = 96.dp) // min height for varying text length
        )
        Row {
            ExtendedFloatingActionButton(
                text = { Text(text = stringResource(R.string.get_new_number)) },
                onClick = { getNewNumber() },
                icon = { Icon(Icons.Filled.Refresh, "Get new number") },
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .width(150.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ExtendedFloatingActionButton(
                text = { Text(text = stringResource(R.string.save)) },
                onClick =
                {
                    if (numberResource?.data != null) {
                        if (numberResource?.data!!.text.isNotBlank()) {
                            addNumberToFirestore(numberResource?.data!!)
                        }
                    }
                },
                icon = { Icon(Icons.Filled.Add, "Save") },
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .width(150.dp)
            )
        }
    }
}

@Composable
private fun TriviaHistoryFab(nc: NavController, vm: NumbersViewModel) {
    FloatingActionButton(onClick = {
        vm.getHistoryFromFirestore()
        nc.navigate(NumberScreens.NumbersHistoryScreen.name)
    }) {
        Text(text = stringResource(R.string.history))
    }
}

