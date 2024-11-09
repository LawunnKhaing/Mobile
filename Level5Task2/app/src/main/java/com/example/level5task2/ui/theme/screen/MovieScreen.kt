package com.example.level5task2.ui.theme.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.level5task2.R
import com.example.level5task2.data.model.Movie
import com.example.level5task2.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(navController: NavController, viewModel: MovieViewModel) {
    // Observe the movies list and provide an initial empty list
    val movies by viewModel.movies.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    var query by remember { mutableStateOf("") }
    var isSearchPerformed by remember { mutableStateOf(false) }
    var showFavoritesOnly by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.DarkGray)
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchView(
                query = query,
                onSearch = { searchQuery ->
                    query = searchQuery
                    viewModel.searchMovies(searchQuery)
                    isSearchPerformed = true
                },
                showFavoritesOnly = showFavoritesOnly,
                onToggleFavorites = {
                    showFavoritesOnly = !showFavoritesOnly
                    if (showFavoritesOnly) {
                        viewModel.showFavoritesOnly()
                    } else {
                        viewModel.searchMovies(query)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Text(text = "Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                if (!isSearchPerformed && !showFavoritesOnly) {
                    Text(
                        text = "Your Favorite Movies\nMaybe None?",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray
                    )
                } else {
                    val filteredMovies = movies

                    if (filteredMovies.isEmpty() && isSearchPerformed) {
                        Text(text = "No movies found", modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        MovieGrid(filteredMovies, navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    query: String,
    onSearch: (String) -> Unit,
    showFavoritesOnly: Boolean,
    onToggleFavorites: () -> Unit
) {
    var text by remember { mutableStateOf(query) }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        placeholder = { Text("Enter movie title...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            IconButton(onClick = { onToggleFavorites() }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Toggle Favorites",
                    tint = if (showFavoritesOnly) Color.Red else Color.Gray
                )
            }
        },
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(text)
        }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGrid(movies: List<Movie>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies) { movie ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate("movie_detail_screen/${movie.id}") },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                    contentDescription = movie.title,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movie.title, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            }
        }
    }
}
