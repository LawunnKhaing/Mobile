package com.example.level2example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.level2example.ui.theme.Level2ExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level2ExampleTheme {
               Surface  (
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ){
                   DiceScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiceScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { padding -> ScreenContent(Modifier.padding(padding)) },
    )
}

@Composable
private fun ScreenContent(modifier: Modifier) {

    var currentDiceValue: Int by remember { mutableIntStateOf(getRandomNumber()) } // Initial dice roll.
    var nrRolls: Int by remember { mutableIntStateOf(1) } // Value 1 after the initial dice roll.
    var totalOfRolledDiceValues: Int by remember { mutableIntStateOf(currentDiceValue) }

    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Instruction()
        ImageToDisplay(currentDiceValue)
        TotalDiceRollsValue(totalOfRolledDiceValues, nrRolls)
        NextRoll(
            updateDiceValuesAndNrRolls = {
                currentDiceValue = it
                nrRolls++
                totalOfRolledDiceValues += currentDiceValue

            }
        )
    }
}

@Composable
private fun Instruction() {
    Text(
        text = stringResource(id = R.string.instruction),
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
private fun ImageToDisplay(currentDiceValue: Int) {
    Image(
        painter = painterResource(diceValueToImageId(currentDiceValue)),
        contentDescription = "dice",
        modifier = Modifier
            .padding(all = 64.dp)
            .width(250.dp)
            .height(250.dp)
    )
}

private fun diceValueToImageId(diceValue: Int): Int {
    val diceValues = arrayOf(
        R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
        R.drawable.dice4, R.drawable.dice5, R.drawable.dice6
    )
    return diceValues[diceValue - 1]
}


@Composable
private fun TotalDiceRollsValue(totalOfRolledDiceValues: Int, nrRolls: Int) {
    Text(
        text = stringResource(
            R.string.total,
            totalOfRolledDiceValues.toString(), nrRolls
        ),
        style = MaterialTheme.typography.bodyLarge,
    )
}


@Composable
private fun NextRoll(updateDiceValuesAndNrRolls: (Int) -> Unit) {
    Button(
        modifier = Modifier.padding(12.dp),
        onClick = {
            updateDiceValuesAndNrRolls(getRandomNumber())
        }
    ) {
        Text(text = stringResource(R.string.next_roll))
    }

}

private fun getRandomNumber(): Int {
    return (1..6).random()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Level2ExampleTheme {
        DiceScreen()
    }
}