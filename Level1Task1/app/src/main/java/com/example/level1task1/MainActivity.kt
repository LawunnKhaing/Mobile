package com.example.level1task1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.level1task1.ui.theme.Level1Task1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level1Task1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    ReminderScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderScreen() {
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
        content = { padding -> ScreenContent(Modifier.padding(padding)) },
    )
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    val context = LocalContext.current
    val reminderAddedText = stringResource(R.string.reminder_added)
    val emptyReminderText = stringResource(R.string.no_empty_reminder)
    var newReminder by remember { mutableStateOf(String()) }
    val newReminderDate = remember { mutableStateOf(String()) }
    val reminders = remember { mutableStateListOf<Reminder>() }

    Column(
        modifier = modifier.padding(16.dp)
    ){
        Column()
        {
            OutlinedTextField(
                value = newReminder,
                placeholder = { Text(text = stringResource(R.string.enter_new_reminder)) },
                onValueChange = { newReminder = it },
                label = { Text(text = stringResource(R.string.enter_new_reminder)) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    if (newReminder.isNotBlank()) {
                        reminders.add(Reminder(newReminder))
                        newReminder = "" // Clear the input field
                        Toast.makeText(context, reminderAddedText, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, emptyReminderText, Toast.LENGTH_SHORT).show()
                    }
                })
            {
                Text(text = stringResource(R.string.add))
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
            }
        }
        LazyColumn {
            items(reminders)
            { reminder ->
                Text(
                    text = reminder.reminderData,
                    Modifier.padding(16.dp)
                )
                Divider(
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.alpha(0.5f),
                    thickness = 1.dp
                )
            }
        }

    }
}

data class Reminder(val reminderData: String)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Level1Task1Theme {
        ReminderScreen()
    }
}
