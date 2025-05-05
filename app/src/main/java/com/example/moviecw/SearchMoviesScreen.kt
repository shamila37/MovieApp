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
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder

class SearchMoviesScreen:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchMovies()
        }
    }
}

@Composable
fun SearchMovies() {
//    Text(text = "Search Movies Screen")

    val context = LocalContext.current

    // Use both below ones for screen orientation part
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    // Data base connection
    val db = remember { MovieAppDB.getInstance(context) }
    val moviesDao = db.moviesDao()

    var inputMovieTitle by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var movieDetails by rememberSaveable { mutableStateOf<MoviesEntity?>(null) }
    var buttonOutput by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.movie_background02),
            contentDescription = "Search Movies Screen background image",
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
                    value = inputMovieTitle,
                    onValueChange = {
                        inputMovieTitle = it
                        buttonOutput = ""
                    },
                    label = { Text("Enter movie title", color = Color.White) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

//                Retrieve Movie Details button
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            buttonOutput = ""
                            val result = searchMoviesFromApi(inputMovieTitle.text)
                            movieDetails = result
                        }
                    }
                ) {
                    Text("Retrieve Movie Details")
                }

                Spacer(modifier = Modifier.height(16.dp))

//                Save Movie Details to Database button
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            movieDetails?.let {
                                moviesDao.insertAll(listOf(it))
                                buttonOutput = "Movie added successfully"
                            }
                        }
                    }
                ) {
                    Text("Save Movie Details to Database")
                }

                Spacer(modifier = Modifier.height(16.dp))

//                To display the search results
                LazyColumn {
                    movieDetails?.let { movie ->
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xAA000000))
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text("Title: ${movie.title}", color = Color.White)
                                Text("Year: ${movie.year}", color = Color.White)
                                Text("Rated: ${movie.rated}", color = Color.White)
                                Text("Released: ${movie.released}", color = Color.White)
                                Text("Runtime: ${movie.runTime}", color = Color.White)
                                Text("Genre: ${movie.genre}", color = Color.White)
                                Text("Director: ${movie.director}", color = Color.White)
                                Text("Writer: ${movie.writer}", color = Color.White)
                                Text("Actors: ${movie.actors}", color = Color.White)
                                Text("Plot: ${movie.plot}", color = Color.White)
                                Text("Language: ${movie.language}", color = Color.White)
                                Text("Country: ${movie.country}", color = Color.White)
                            }
                        }
                    }
                }

                if (buttonOutput.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = buttonOutput, color = Color.White,
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
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        OutlinedTextField(
                            value = inputMovieTitle,
                            onValueChange = {
                                inputMovieTitle = it
                                buttonOutput = ""
                            },
                            label = { Text("Enter movie title", color = Color.White) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

//                        Retrieve Movie Details button
                        Button(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    buttonOutput = ""
                                    val result = searchMoviesFromApi(inputMovieTitle.text)
                                    movieDetails = result
                                }
                            }
                        ) {
                            Text("Retrieve Movie Details")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

//                        Save Movie Details to Database button
                        Button(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    movieDetails?.let {
                                        moviesDao.insertAll(listOf(it))
                                        buttonOutput = "Movie added successfully"
                                    }
                                }
                            }
                        ) {
                            Text("Save Movie Details to Database")
                        }

                        if (buttonOutput.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = buttonOutput, color = Color.White,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
//                    Text("Right side", fontSize = 40.sp)
//                    To display the search results
                    LazyColumn {
                        movieDetails?.let { movie ->
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color(0xAA000000))
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text("Title: ${movie.title}", color = Color.White)
                                    Text("Year: ${movie.year}", color = Color.White)
                                    Text("Rated: ${movie.rated}", color = Color.White)
                                    Text("Released: ${movie.released}", color = Color.White)
                                    Text("Runtime: ${movie.runTime}", color = Color.White)
                                    Text("Genre: ${movie.genre}", color = Color.White)
                                    Text("Director: ${movie.director}", color = Color.White)
                                    Text("Writer: ${movie.writer}", color = Color.White)
                                    Text("Actors: ${movie.actors}", color = Color.White)
                                    Text("Plot: ${movie.plot}", color = Color.White)
                                    Text("Language: ${movie.language}", color = Color.White)
                                    Text("Country: ${movie.country}", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

suspend fun searchMoviesFromApi(title: String): MoviesEntity? = withContext(Dispatchers.IO) {
    val apiKey = "3bbb9aad" // My api key from omdbapi.com
    val encodedTitle = URLEncoder.encode(title, "UTF-8")
    val url =
        "https://www.omdbapi.com/?t=$encodedTitle&apikey=$apiKey"  // t to get one specific movie

    try {
        val json = URL(url).readText()
        val obj = JSONObject(json)

        if (obj.getString("Response") == "True") {
            MoviesEntity(
                imdbID = obj.optString("imdbID", ""),
                title = obj.optString("Title", ""),
                year = obj.optString("Year", ""),
                rated = obj.optString("Rated", ""),
                released = obj.optString("Released", ""),
                runTime = obj.optString("Runtime", ""),
                genre = obj.optString("Genre", ""),
                director = obj.optString("Director", ""),
                writer = obj.optString("Writer", ""),
                actors = obj.optString("Actors", ""),
                plot = obj.optString("Plot", ""),
                language = obj.optString("Language", ""),
                country = obj.optString("Country", ""),
                awards = obj.optString("Awards", ""),
                imdbRating = obj.optString("imdbRating", ""),
                imdbVotes = obj.optString("imdbVotes", ""),
                type = obj.optString("Type", ""),
                totalSeasons = obj.optString("totalSeasons", ""),
                response = obj.optString("Response", "")
            )
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}


//w1986643/20223147
//Shamila Ashan Gunarathna