package com.example.level4task2.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.level4task2.R
import com.example.level4task2.data.Game
import com.example.level4task2.viewmodel.GameViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: GameViewModel) {
    val games = viewModel.games.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.height(56.dp),
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(games.value) { game ->
                    GameItem(game = game, viewModel = viewModel)
                }
            }
        }
    )
}

@Composable
fun GameItem(game: Game, viewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = when (game.result) {
                "win" -> stringResource(id = R.string.you_win)
                "lose" -> stringResource(id = R.string.computer_wins)
                "draw" -> stringResource(id = R.string.draw)
                else -> ""
            },
            style = MaterialTheme.typography.headlineMedium,
            color = if (game.result == "win") Color.Green else Color.Red
        )

        Text(
            text = formatDate(game.date),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.computer))
                Image(
                    painter = getMoveImage(game.computerMove),
                    contentDescription = game.computerMove,
                    modifier = Modifier.size(80.dp)
                )
            }

            Text(text = stringResource(id = R.string.vs), style = MaterialTheme.typography.headlineSmall)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.you))
                Image(
                    painter = getMoveImage(game.playerMove),
                    contentDescription = game.playerMove,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(onClick = { viewModel.deleteGame(game) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete),
                tint = Color.Gray
            )
        }
    }
}

fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
    return sdf.format(date)
}


@Composable
fun getMoveImage(move: String) = when (move) {
    "Rock" -> painterResource(id = R.drawable.rock)
    "Paper" -> painterResource(id = R.drawable.paper)
    "Scissors" -> painterResource(id = R.drawable.scissors)
    else -> painterResource(id = R.drawable.rock)
}
