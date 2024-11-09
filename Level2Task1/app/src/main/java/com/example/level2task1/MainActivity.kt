package com.example.level2task1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.level2task1.ui.theme.Level2Task1Theme
import androidx.compose.ui.platform.LocalContext


data class Statement(
    val statement: String,
    val isTrue: Boolean
)

private fun generateStatements(): ArrayList<Statement> {
    return arrayListOf(
        Statement("A \'val\' and \'var\' are the same.", false),
        Statement("Mobile Application Development grants 12 ECTS.", false),
        Statement("A unit in Kotlin corresponds to a void in Java.", true),
        Statement("In Kotlin \'when\' replaces the \'switch\' operator in Java.", true)
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level2Task1Theme {
               Surface  (
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ){
                   QuizScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { padding -> ScreenContent(Modifier.padding(padding))}
            )

}

@Composable
private fun ScreenContent(modifier: Modifier = Modifier) {

    val quizStatements = remember { mutableStateListOf(*generateStatements().toTypedArray()) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        QuizInstructionsHeader()
        QuizStatements(
            localQuizStatements = quizStatements,
            removeQuizStatement = { quizStatements.remove(it) }
        )
    }
}

@Composable
private fun QuizInstructionsHeader() {
    Text(
        text = stringResource(R.string.quiz_instr_header),
        style = MaterialTheme.typography.headlineLarge
    )
    Text(
        text = stringResource(R.string.quiz_instr_description),
        style = MaterialTheme.typography.bodyLarge
    )
}

private fun informUser(context: Context, msgId: Int) {
    Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show()
}

@Composable
private fun QuizStatements(
    localQuizStatements: MutableList<Statement>,
    removeQuizStatement: (Statement) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        items(localQuizStatements) { statement ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = statement.statement,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green,
                            contentColor = Color.White
                        ),
                        onClick = {
                            val message = if (statement.isTrue) {
                                removeQuizStatement(statement)
                                R.string.answer_is_true
                            } else {
                                R.string.wrong_answer
                            }
                            informUser(context, message)
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(id = R.string.button_true))
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        onClick = {
                            val message = if (!statement.isTrue) {
                                removeQuizStatement(statement)
                                R.string.answer_is_false
                            } else {
                                R.string.wrong_answer
                            }
                            informUser(context, message)
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(id = R.string.button_false))
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Level2Task1Theme {
        QuizScreen()
    }
}