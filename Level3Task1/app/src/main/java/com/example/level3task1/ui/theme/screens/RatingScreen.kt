package com.example.level3task1.ui.theme.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.level3task1.R
import com.example.level3task1.viewmodel.GameViewModel
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingScreen(navController: NavHostController, viewModel: GameViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { innerPadding ->
            ScreenContent(Modifier.padding(innerPadding), navController, viewModel)
        }
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: GameViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.rate_game),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = viewModel.readRandomlyChosenGame(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        RatingBar(
            value = viewModel.readGameRatingAccordingToUser(),
            config = RatingBarConfig()
                .stepSize(StepSize.HALF)
                .numStars(5)
                .style(RatingBarStyle.HighLighted),
            onValueChange = { rating ->
                viewModel.saveGameRatingAccordingToUser(rating)
            },
            onRatingChanged = { rating ->
                viewModel.saveGameRatingAccordingToUser(rating)
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                navController.navigate(GameRatingScreens.SummaryScreen.name)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.to_summary))
        }
    }
}









