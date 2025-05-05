package com.example.moviecw

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecw.MovieDB.MovieAppDB
import com.example.moviecw.MovieDB.MoviesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMoviesScreen:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddMovies()
        }
    }
}

@Composable
fun AddMovies() {
//    Text(text = "Add Movies Screen")

    val context = LocalContext.current

    // Data base connection
    val db = remember { MovieAppDB.getInstance(context) }
    val moviesDao = db.moviesDao()

    val scope = rememberCoroutineScope()
    var buttonOutput by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.movie_background02),
            contentDescription = "Add Movies Screen background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Add Movies to the Database button
            Button(
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        val movies = getHardcodedMovieList()
                        moviesDao.insertAll(movies)
                        Log.d("Movies", "Movies added successfully: ${movies.size}")
                        buttonOutput = "Movies added successfully"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Movies to the Database")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (buttonOutput.isNotEmpty()) {
                Text(
                    text = buttonOutput,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Go back to home button
            Button(
                onClick = {
                    (context as Activity).finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Go Back to Home Page")
            }
        }
    }
}

// Add hardcoded movie details to here from movies.txt file
fun getHardcodedMovieList(): List<MoviesEntity> {
    return listOf(
        MoviesEntity(
            imdbID = "tt0106062",
            title = "The Matrix",
            year = "1993",
            rated = "R",
            released = "01 Mar 1993",
            runTime = "60 min",
            genre = "Action, Sci-Fi",
            director = "N/A",
            writer = "Grenville Case",
            actors = "Nick Mancuso, Phillip Jarrett, Carrie-Anne Moss",
            plot = "Steven Matrix is\n" +
                    " one of the underworld’s foremost hitmen until his luck runs out, and\n" +
                    " someone puts a contract out on him. Shot in the forehead by a .22\n" +
                    " pistol, Matrix \\\"dies\\\" and finds himself in \\\"The City In Between\\\",\n" +
                    " where he is sho",
            language = "English",
            country = "Canada",
            awards = "1",
            imdbRating = "8.0",
            imdbVotes = "185",
            type = "movie",
            totalSeasons = "1",
            response = "True"
        ),
    )
}


//w1986643/20223147
//Shamila Ashan Gunarathna
