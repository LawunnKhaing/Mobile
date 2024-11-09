package com.example.task2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task2.ui.theme.Task2Theme

data class Place(
    val cityName: String,
    val placeOfInterest: String,
    val imageId: Int
)

private fun generatePlaces(): List<Place> {
    return listOf(
        Place("Amsterdam", "Dam", R.drawable.amsterdam_dam),
        Place("Amsterdam", "Weesperplein", R.drawable.amsterdam_weesperplein),
        Place("Rotterdam", "Euromast", R.drawable.rotterdam_euromast),
        Place("Den Haag", "Binnenhof", R.drawable.den_haag_binnenhof),
        Place("Utrecht", "Dom", R.drawable.utrecht_dom),
        Place("Groningen", "Martinitoren", R.drawable.groningen_martinitoren),
        Place("Maastricht", "Vrijthof", R.drawable.maastricht_vrijthof),
        Place("New York", "Vrijheidsbeeld", R.drawable.new_york_vrijheidsbeeld),
        Place("San Francisco", "Golden Gate", R.drawable.san_francisco_golden_gate),
        Place("Yellowstone", "Old Faithful", R.drawable.yellowstone_old_faithful),
        Place("Yosemite", "Half Dome", R.drawable.yosemite_half_dome),
        Place("Washington", "White House", R.drawable.washington_white_house),
        Place("Ottawa", "Parliament Hill", R.drawable.ottawa_parliament_hill),
        Place("Londen", "Tower Bridge", R.drawable.london_tower_bridge),
        Place("Brussel", "Manneken Pis", R.drawable.brussel_manneken_pis),
        Place("Berlijn", "Reichstag", R.drawable.berlijn_reichstag),
        Place("Parijs", "Eiffeltoren", R.drawable.parijs_eiffeltoren),
        Place("Barcelona", "Sagrada Familia", R.drawable.barcelona_sagrada_familia),
        Place("Rome", "Colosseum", R.drawable.rome_colosseum),
        Place("Napels", "Pompeii", R.drawable.pompeii),
        Place("Kopenhagen", "", R.drawable.kopenhagen),
        Place("Oslo", "", R.drawable.oslo),
        Place("Stockholm", "", R.drawable.stockholm),
        Place("Helsinki", "", R.drawable.helsinki),
        Place("Moskou", "Rode Plein", R.drawable.moskou_rode_plein),
        Place("Beijing", "Verboden Stad", R.drawable.beijing_verboden_stad),
        Place("Kaapstad", "Tafelberg", R.drawable.kaapstad_tafelberg),
        Place("Rio de Janeiro", "Copacabana", R.drawable.rio_de_janeiro_copacabana),
        Place("Sydney", "Opera", R.drawable.sydney_opera),
        Place("Hawaii", "Honolulu", R.drawable.hawaii),
        Place("Alaska", "Denali", R.drawable.alaska_denali)
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlaceScreen()
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val places = remember { mutableStateListOf<Place>() }
    val allPlaces = generatePlaces()
    val keyboardController = LocalSoftwareKeyboardController.current
    remember { places.addAll(allPlaces) }

            SearchBar(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },

                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                    val filteredPlaces = if (newQuery.isBlank()) {
                        allPlaces
                    } else {
                        allPlaces.filter {
                            it.cityName.contains(newQuery, ignoreCase = true) ||
                                    it.placeOfInterest.contains(newQuery, ignoreCase = true)
                    }
                    }
                    places.clear()
                    places.addAll(filteredPlaces)
                },
                onSearch = {
                    keyboardController?.hide()
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color.LightGray
                ),
                placeholder = {Text("Search for places")},
                shape = RoundedCornerShape(8.dp),
                active = true,
                onActiveChange = {}
            ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(places) { place ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(id = place.imageId),
                        contentDescription = null,
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                    Text(text = place.cityName, style = MaterialTheme.typography.headlineMedium)
                    Text(text = place.placeOfInterest, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Task2Theme {
        PlaceScreen()
    }
}