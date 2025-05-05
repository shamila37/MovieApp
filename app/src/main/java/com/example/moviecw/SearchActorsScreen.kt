package com.example.moviecw

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecw.MovieDB.MovieAppDB
import com.example.moviecw.MovieDB.MoviesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActorsScreen:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchActors()
        }
    }
}

@Composable
fun SearchActors() {
//    Text(text = "Search Actors Screen")

    val context = LocalContext.current

    // Use both below ones for screen orientation part
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    // Data base connection
    val db = remember { MovieAppDB.getInstance(context) }
    val moviesDao = db.moviesDao()

    var inputActorName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var searchResults by rememberSaveable { mutableStateOf<List<MoviesEntity>>(emptyList()) }
    val scope = rememberCoroutineScope()
    var status by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.movie_background02),
            contentDescription = "Search actors Screen background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (isPortrait) { // Device on portrait screen orientation
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = inputActorName,
                    onValueChange = {
                        inputActorName = it
                        status = ""
                    },
                    label = { Text("Enter actor name", color = Color.White) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

//                Search Actor button
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            status = "Searching..."
                            val results = moviesDao.searchByActor(inputActorName.text)
                            searchResults = results
                            status = if (results.isEmpty()) "No results found." else ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Search Actor")
                }

                Spacer(modifier = Modifier.height(16.dp))

//                To display the search results
                LazyColumn {
                    items(searchResults.size) { index ->
                        val movie = searchResults[index]
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xAA000000))
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "${movie.title} (${movie.year})\nActors: ${movie.actors}",
                                color = Color.White
                            )
                        }
                    }
                }

                if (status.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = status, color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }

        } else { // Device on landscape screen orientation

            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
//                    Text("Left side", fontSize = 40.sp)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = inputActorName,
                            onValueChange = {
                                inputActorName = it
                                status = ""
                            },
                            label = { Text("Enter actor name", color = Color.White) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

//                        Search Actor button
                        Button(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    status = "Searching..."
                                    val results = moviesDao.searchByActor(inputActorName.text)
                                    searchResults = results
                                    status = if (results.isEmpty()) "No results found." else ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Search Actor")
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
//                    Text("Right side", fontSize = 40.sp)

                    if (status.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = status, color = Color.White,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

//                    To display the search results
                    LazyColumn {
                        items(searchResults.size) { index ->
                            val movie = searchResults[index]
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xAA000000))
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    "${movie.title} (${movie.year})\nActors: ${movie.actors}",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


//w1986643/20223147
//Shamila Ashan Gunarathna