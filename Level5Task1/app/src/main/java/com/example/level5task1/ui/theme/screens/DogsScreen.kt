package com.example.level5task1.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.level5task1.R
import com.example.level5task1.data.model.Dog
import com.example.level5task1.repository.Resource
import com.example.level5task1.viewmodel.DogsViewModel


@Composable
fun DogsScreen(viewModel: DogsViewModel) {
    val dogResource: Resource<Dog>? by viewModel.dogResource.observeAsState()

    val dogText = when (dogResource) {
        is Resource.Success -> {
            val url = (dogResource as Resource.Success<Dog>).data?.randomDogPictureUrl
            val breed = url?.split("/")?.dropLast(1)?.last()?.replace("-", " ")?.capitalize()
            breed ?: stringResource(R.string.unknown_breed)
        }
        is Resource.Error -> stringResource(R.string.something_wrong_state)
        is Resource.Loading -> stringResource(R.string.loading_text)
        is Resource.Empty -> stringResource(R.string.empty_pet_placeholder_dog)
        else -> stringResource(R.string.unknown_breed)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.dog_breed),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Text(
            text = dogText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        if (dogResource is Resource.Success) {
            val imageUrl = (dogResource as Resource.Success<Dog>).data?.randomDogPictureUrl
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp) // Spacing after image
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { viewModel.getNewDog() }) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Reload Icon",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = stringResource(R.string.get_new_dog))
        }
    }
}
