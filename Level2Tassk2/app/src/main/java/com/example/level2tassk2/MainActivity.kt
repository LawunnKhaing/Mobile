package com.example.level2tassk2

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.level2tassk2.ui.theme.Level2Tassk2Theme

data class Statement(
    val statement: String,
    val isTrue: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level2Tassk2Theme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ){
                   TableScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TableScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { padding -> ScreenContent(Modifier.padding(padding)) })

}

@Composable
private fun ScreenContent(modifier: Modifier = Modifier) {
    var selectedOption by remember { mutableStateOf(false) }
    var totalCorrect by remember { mutableStateOf(0) }
    var currentBooleanPair by remember { mutableStateOf(getRandomBooleanPair()) }
    var currentOperation by remember { mutableStateOf(getRandomOperation()) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        DisplayBooleanPairAndOperation(currentBooleanPair, currentOperation)
        Spacer(modifier = Modifier.height(16.dp))
        TableButton(
            selectedOption = selectedOption,
            onOptionSelected = { selected -> selectedOption = selected }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CheckAnswer(
            selectedOption = selectedOption,
            currentBooleanPair = currentBooleanPair,
            currentOperation = currentOperation,
            onCorrect = { totalCorrect++ },
            onNewBooleanPair = { currentBooleanPair = getRandomBooleanPair() },
            onNewOperation = { currentOperation = getRandomOperation() }
        )
        NewGame(onNewGame = {
            currentBooleanPair = getRandomBooleanPair()
            currentOperation = getRandomOperation()
        })
        Reset(onReset = { totalCorrect = 0 })
        Spacer(modifier = Modifier.height(16.dp))
        TotalCorrectAnswer(totalCorrect)
    }
}

@Composable
private fun DisplayBooleanPairAndOperation(currentBooleanPair: Pair<Boolean, Boolean>, currentOperation: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = if (currentBooleanPair.first) "T" else "F", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = currentOperation, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = if (currentBooleanPair.second) "T" else "F", style = MaterialTheme.typography.headlineMedium)
    }
}

private fun getRandomBooleanPair(): Pair<Boolean, Boolean> {
    return Pair(getRandomBoolean(), getRandomBoolean())
}

private fun getRandomBoolean(): Boolean {
    return listOf(true, false).random()
}

private fun getRandomOperation(): String {
    return listOf("&&", "||").random()
}

@Composable
private fun TableButton(selectedOption: Boolean, onOptionSelected: (Boolean) -> Unit) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            RadioButton(
                selected = selectedOption,
                onClick = { onOptionSelected(true) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(id = R.string.true_label))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            RadioButton(
                selected = !selectedOption,
                onClick = { onOptionSelected(false) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(id = R.string.false_label))
        }
    }
}

@Composable
private fun Reset(onReset: () -> Unit) {
   Button(onClick = onReset, modifier = Modifier.fillMaxWidth()) {
       Text(text = stringResource(id = R.string.reset))
   }
}

@Composable
private fun NewGame(onNewGame: () -> Unit) {
    Button(onClick = onNewGame, modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.new_game))
    }

}

@Composable
private fun CheckAnswer(
    selectedOption: Boolean,
    currentBooleanPair: Pair<Boolean, Boolean>,
    currentOperation: String,
    onCorrect: () -> Unit,
    onNewBooleanPair: () -> Unit,
    onNewOperation: () -> Unit
) {
    val correctAnswer = when (currentOperation) {
        "&&" -> currentBooleanPair.first && currentBooleanPair.second
        "||" -> currentBooleanPair.first || currentBooleanPair.second
        else -> false
    }

    Button(onClick = {
        if (selectedOption == correctAnswer) {
            onCorrect()
        }
        onNewBooleanPair()
        onNewOperation()
    }, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.check_answer))
    }
}

@Composable
private fun TotalCorrectAnswer(totalCorrect: Int) {
    Text(text = stringResource(id = R.string.total_correct_answers, totalCorrect))
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Level2Tassk2Theme {
       TableScreen()
    }
}