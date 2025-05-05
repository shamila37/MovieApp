//Vide0 link :-

package com.example.moviecw

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecw.ui.theme.MovieCWTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieCWTheme {
                HomePage()
            }
        }
    }
}

@Composable
fun HomePage() {
//    Text(text = "Home Screen")

    val context = LocalContext.current

    // Use both below ones for screen orientation part
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.movie_background01),
            contentDescription = "Home screen background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (isPortrait) { // Device on portrait screen orientation

            Text(         // App name
                "🎬 Movie App 🎬",
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(top = 160.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(   // Add movie to database button
                    onClick = {
                        val intent = Intent(context, AddMoviesScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .size(width = 280.dp, height = 80.dp)
                        .padding(all = 10.dp)
                ) {
                    Text(text = "Add Movies to DB", fontSize = 18.sp)
                }

                Button(   // Search for movies button
                    onClick = {
                        val intent = Intent(context, SearchMoviesScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .size(width = 280.dp, height = 80.dp)
                        .padding(all = 10.dp)
                ) {
                    Text(text = "Search for Movies", fontSize = 18.sp)
                }

                Button(   // Search for actors button
                    onClick = {
                        val intent = Intent(context, SearchActorsScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .size(width = 280.dp, height = 80.dp)
                        .padding(all = 10.dp)
                ) {
                    Text(text = "Search for Actors", fontSize = 18.sp)
                }

                Button(   // Search movies from web button
                    onClick = {
                        val intent = Intent(context, SearchWebMoviesScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .size(width = 280.dp, height = 80.dp)
                        .padding(all = 10.dp)
                ) {
                    Text(text = "Search Movies from web", fontSize = 18.sp)
                }
            }

        } else { // Device on landscape screen orientation

            Text(
                "🎬 Movie App 🎬",
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(top = 80.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row { // Button row-1
                    Button(   // Add movie to database button
                        onClick = {
                            val intent = Intent(context, AddMoviesScreen::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 80.dp)
                            .padding(all = 10.dp)
                    ) {
                        Text(text = "Add Movies to DB", fontSize = 18.sp)
                    }

                    Button(   // Search for movies button
                        onClick = {
                            val intent = Intent(context, SearchMoviesScreen::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 80.dp)
                            .padding(all = 10.dp)
                    ) {
                        Text(text = "Search for Movies", fontSize = 18.sp)
                    }
                }

                Row { // Button row-2
                    Button(   // Search for actors button
                        onClick = {
                            val intent = Intent(context, SearchActorsScreen::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 80.dp)
                            .padding(all = 10.dp)
                    ) {
                        Text(text = "Search for Actors", fontSize = 18.sp)
                    }

                    Button(   // Search movies from web button
                        onClick = {
                            val intent = Intent(context, SearchWebMoviesScreen::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 80.dp)
                            .padding(all = 10.dp)
                    ) {
                        Text(text = "Search Movies from web", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieCWTheme {
        HomePage()
    }
}


//w1986643/20223147
//Shamila Ashan Gunarathna