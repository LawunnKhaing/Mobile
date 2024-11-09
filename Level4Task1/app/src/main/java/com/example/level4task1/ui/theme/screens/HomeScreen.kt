package com.example.level4task1.ui.theme.screens


import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.level4task1.R
import com.example.level4task1.data.Game
import com.example.level4task1.viewmodel.GameViewModel
import com.example.level4task1.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: GameViewModel) {
    val context = LocalContext.current
    val games = viewModel.gameBacklog
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var deletedBacklog by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.DarkGray,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.app_name), color = Color.White)
                        IconButton(onClick = {
                            deletedBacklog = true
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.snackbar_msg),
                                    actionLabel = context.getString(R.string.undo),
                                    duration = SnackbarDuration.Long
                                )
                                if (result != SnackbarResult.ActionPerformed) {
                                    viewModel.deleteGameBacklog()  // Remove all games from the database
                                }
                                deletedBacklog = false
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete all games",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddGameScreen.route) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        content = { innerPadding ->
            Modifier.padding(innerPadding)

            if (!deletedBacklog) {
                Games(
                    context = context,
                    games = games,
                    modifier = Modifier.padding(16.dp),
                    viewModel = viewModel,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    )
}

@Composable
fun Games(
    context: Context,
    games: LiveData<List<Game>>,
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: GameViewModel
) {
    val gamesState by games.observeAsState()

    LazyColumn(
        modifier = modifier
            .padding(top = 120.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        gamesState?.sortedBy { it.release }.let { games ->
            if (games != null) {
                itemsIndexed(
                    items = games,
                    key = { _, game -> game.hashCode() }
                ) { _, game ->
                    GameCard(context, game = game, snackbarHostState = snackbarHostState, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(
    context: Context,
    game: Game,
    snackbarHostState: SnackbarHostState,
    viewModel: GameViewModel
) {
    val swipeState = rememberSwipeToDismissBoxState() //rememberDismissState()
    if (swipeState.dismissDirection.equals(SwipeToDismissBoxValue.StartToEnd) ||
        swipeState.dismissDirection.equals(SwipeToDismissBoxValue.EndToStart)
    ) {
        LaunchedEffect(Unit) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.deleted_game, game.title),
                actionLabel = context.getString(R.string.undo),
                duration = SnackbarDuration.Long
            )
            if (result != SnackbarResult.ActionPerformed) {
                viewModel.deleteGame(game)
            } else {
                swipeState.reset()
            }
        }
    }

    SwipeToDismissBox(
        state = swipeState,
        backgroundContent = {},
        content = {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = game.title, style = MaterialTheme.typography.headlineSmall,
                        fontStyle = FontStyle.Italic
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = game.platform)
                        Text(text = "Release: " + Utils.dateToString(game.release))
                    }
                }
            }
        },
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = true
    )
}