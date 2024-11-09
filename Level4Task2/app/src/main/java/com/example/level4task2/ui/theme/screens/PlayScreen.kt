package com.example.level4task2.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.level4task2.R
import com.example.level4task2.data.Game
import com.example.level4task2.viewmodel.GameViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(viewModel: GameViewModel) {
    val playerMove = remember { mutableStateOf("") }
    val computerMove = remember { mutableStateOf("") }
    val gameResult = remember { mutableStateOf("") }

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Text(stringResource(id = R.string.instructions), style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "${stringResource(id = R.string.win)}: ${viewModel.wins} " +
                            "${stringResource(id = R.string.draw)}: ${viewModel.draws} " +
                            "${stringResource(id = R.string.lose)}: ${viewModel.losses}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(stringResource(id = R.string.computer), style = MaterialTheme.typography.bodyLarge)
                        Image(
                            painter = when (computerMove.value) {
                                "Rock" -> painterResource(id = R.drawable.rock)
                                "Paper" -> painterResource(id = R.drawable.paper)
                                "Scissors" -> painterResource(id = R.drawable.scissors)
                                else -> painterResource(id = R.drawable.rock) // Default placeholder
                            },
                            contentDescription = computerMove.value,
                            modifier = Modifier.size(100.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    Text(
                        text = stringResource(id = R.string.vs),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.width(32.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(id = R.string.you), style = MaterialTheme.typography.bodyLarge)
                        Image(
                            painter = when (playerMove.value) {
                                "Rock" -> painterResource(id = R.drawable.rock)
                                "Paper" -> painterResource(id = R.drawable.paper)
                                "Scissors" -> painterResource(id = R.drawable.scissors)
                                else -> painterResource(id = R.drawable.rock) // Default placeholder
                            },
                            contentDescription = playerMove.value,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = when (gameResult.value) {
                        "win" -> stringResource(id = R.string.win)
                        "lose" -> stringResource(id = R.string.lose)
                        "draw" -> stringResource(id = R.string.draw)
                        else -> ""
                    },
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.rock),
                        contentDescription = stringResource(id = R.string.rock),
                        modifier = Modifier
                            .size(100.dp)
                            .clickable { playGame("Rock", playerMove, computerMove, gameResult, viewModel) }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(R.drawable.paper),
                        contentDescription = stringResource(id = R.string.paper),
                        modifier = Modifier
                            .size(100.dp)
                            .clickable { playGame("Paper", playerMove, computerMove, gameResult, viewModel) }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(R.drawable.scissors),
                        contentDescription = stringResource(id = R.string.scissors),
                        modifier = Modifier
                            .size(100.dp)
                            .clickable { playGame("Scissors", playerMove, computerMove, gameResult, viewModel) }
                    )
                }
            }
        }
    )
}

fun playGame(
    playerChoice: String,
    playerMove: MutableState<String>,
    computerMove: MutableState<String>,
    gameResult: MutableState<String>,
    viewModel: GameViewModel
) {
    val computerChoice = getRandomMove()
    val result = determineResult(playerChoice, computerChoice)

    playerMove.value = playerChoice
    computerMove.value = computerChoice
    gameResult.value = result

    val game = Game(date = Date(), playerMove = playerChoice, computerMove = computerChoice, result = result)
    viewModel.insertGame(game)

    when (result) {
        "win" -> viewModel.incrementWins()
        "lose" -> viewModel.incrementLosses()
        "draw" -> viewModel.incrementDraws()
    }
}

private fun getRandomMove(): String {
    return listOf("Rock", "Paper", "Scissors").random()
}

private fun determineResult(playerMove: String, computerMove: String): String {
    return when {
        playerMove == computerMove -> "draw"
        (playerMove == "Rock" && computerMove == "Scissors") ||
                (playerMove == "Paper" && computerMove == "Rock") ||
                (playerMove == "Scissors" && computerMove == "Paper") -> "win"
        else -> "lose"
    }
}