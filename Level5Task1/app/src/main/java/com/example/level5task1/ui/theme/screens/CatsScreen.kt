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
import com.example.level5task1.data.api.Api
import com.example.level5task1.data.model.Cat
import com.example.level5task1.repository.Resource
import com.example.level5task1.viewmodel.CatsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CatsScreen(viewModel: CatsViewModel) {
    val catResource: Resource<Cat>? by viewModel.catResource.observeAsState()

    val (catText, formattedDate) = when (catResource) {
        is Resource.Success -> {
            val dateStr = (catResource as Resource.Success<Cat>).data?.creationDate ?: ""
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val parsedDate = formatter.parse(dateStr)
            val userFriendlyDate = parsedDate?.let {
                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(it)
            } ?: "Unknown Date"

            stringResource(R.string.cats_screen_header) to userFriendlyDate
        }
        is Resource.Error -> stringResource(R.string.something_wrong_state) to null
        is Resource.Loading -> stringResource(R.string.loading_text) to null
        is Resource.Empty -> stringResource(R.string.empty_pet_placeholder_cat) to null
        else -> "" to null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.cats_screen_header),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        formattedDate?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (catResource is Resource.Success) {
            val imageUrl = Api.CATS_BASE_URL + "cat/" + (catResource as Resource.Success<Cat>).data?.urlExtension
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))


        Button(onClick = { viewModel.getNewCat() }) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Reload Icon",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = stringResource(R.string.get_new_cat))
        }
    }
}