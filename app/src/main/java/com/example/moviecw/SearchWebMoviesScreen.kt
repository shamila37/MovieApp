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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecw.MovieDB.MoviesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder

class SearchWebMoviesScreen:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchWebMovies()
        }
    }
}

@Composable
fun SearchWebMovies() {
//    Text(text = "Search Movies from Web Screen")

    // Use both below ones for screen orientation part
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var inputWebMovieTitle by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var movieDetails by rememberSaveable { mutableStateOf<List<MoviesEntity>>(emptyList()) }
    var buttonOutput by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.movie_background02),
            contentDescription = "Search Movies from Web Screen background image",
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
                    value = inputWebMovieTitle,
                    onValueChange = {
                        inputWebMovieTitle = it
                        buttonOutput = ""
                    },
                    label = { Text("Enter movie title", color = Color.White) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

//                Search movies from Web button
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            buttonOutput = "Searching..."
                            val result = searchMoviesFromWeb(inputWebMovieTitle.text)
                            movieDetails = result
                            buttonOutput = if (result.isEmpty()) "No results found." else ""
                        }
                    }
                ) {
                    Text("Search movies from Web")
                }

                Spacer(modifier = Modifier.height(16.dp))

//                To display the search results
                LazyColumn {
                    items(movieDetails) { movie ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xAA000000))
                                .padding(15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text("Title: ${movie.title}", color = Color.White)
                            Text("Year: ${movie.year}", color = Color.White)
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
                            value = inputWebMovieTitle,
                            onValueChange = {
                                inputWebMovieTitle = it
                                buttonOutput = ""
                            },
                            label = { Text("Enter movie title", color = Color.White) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

//                        Search movies from Web
                        Button(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    buttonOutput = "Searching..."
                                    val result = searchMoviesFromWeb(inputWebMovieTitle.text)
                                    movieDetails = result
                                    buttonOutput = if (result.isEmpty()) "No results found." else ""
                                }
                            }
                        ) {
                            Text("Search movies from Web")
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

//                    To display the search results
                    LazyColumn {
                        items(movieDetails) { movie ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xAA000000))
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text("Title: ${movie.title}", color = Color.White)
                                Text("Year: ${movie.year}", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

suspend fun searchMoviesFromWeb(title: String): List<MoviesEntity> = withContext(Dispatchers.IO) {
    val apiKey = "3bbb9aad" // My api key from omdbapi.com
    val encodedTitle = URLEncoder.encode(title, "UTF-8")
    val url = "https://www.omdbapi.com/?s=$encodedTitle&apikey=$apiKey"  // s to get more movies

    try {
        val json = URL(url).readText()
        val obj = JSONObject(json)

        if (obj.getString("Response") == "True") {
            val searchArray = obj.getJSONArray("Search")
            val resultList = mutableListOf<MoviesEntity>()

            for (i in 0 until searchArray.length()) {
                val item = searchArray.getJSONObject(i)
                resultList.add(
                    MoviesEntity(
                        imdbID = item.optString("imdbID", ""),
                        title = item.optString("Title", ""),
                        year = item.optString("Year", ""),
                        rated = "",
                        released = "",
                        runTime = "",
                        genre = "",
                        director = "",
                        writer = "",
                        actors = "",
                        plot = "",
                        language = "",
                        country = "",
                        awards = "",
                        imdbRating = "",
                        imdbVotes = "",
                        type = item.optString("Type", ""),
                        totalSeasons = "",
                        response = obj.optString("Response", "")
                    )
                )
            }
            resultList
        }else{
            emptyList()
        }
    } catch (e: Exception) {
        emptyList()
    }
}


//w1986643/20223147
//Shamila Ashan Gunarathna
