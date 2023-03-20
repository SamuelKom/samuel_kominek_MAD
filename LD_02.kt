package com.example.movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movie_app.ui.theme.Movie_AppTheme
import com.example.testapp.models.Movie
import com.example.testapp.models.getMovies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Movie_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TopAppBar()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Movie_AppTheme {
        TopAppBar()
    }
}

@Composable
fun MovieRow(movie: Movie) {
    var icon by remember { mutableStateOf(Icons.Default.KeyboardArrowUp) }
    var visibility by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column {
            Box(modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
            ) {
                /*Image(
                    painter = painterResource(id = R.drawable.avatar2),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )*/
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.images[0])
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.avatar2),
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.Crop
                )

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ){
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(movie.title, style = MaterialTheme.typography.h6)
                Icon(
                    imageVector = icon,
                    contentDescription = "Show details",
                    modifier = Modifier
                        .clickable {
                            if(icon == Icons.Default.KeyboardArrowUp){
                                icon = Icons.Default.KeyboardArrowDown
                                visibility = true
                            }else{
                                icon = Icons.Default.KeyboardArrowUp
                                visibility = false
                            }
                        }
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
            ) {
                AnimatedVisibility(visible = visibility,
                    enter = slideInVertically() + expandVertically() + fadeIn(),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(text = "Director: " + movie.director)
                        Text(text = "Released: " + movie.year)
                        Text(text = "Genre: " + movie.genre)
                        Text(text = "Actors: " + movie.actors)
                        Text(text = "Rating: " + movie.rating)
                        Divider(color = Color.LightGray, modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp))
                        Text(text = "Plot: " + movie.plot)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieList(){
    val list : List<Movie> = getMovies()
    LazyColumn {
        items(list) {
            MovieRow(movie = it)
        }
    }
}

@Composable
fun TopAppBar() {
    var dropDown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Movies", color = Color.White) },
            backgroundColor = colorResource(id = R.color.purple_700),
            contentColor = Color.White,
            elevation = 12.dp,
            actions = {
                IconButton(onClick = { dropDown = !dropDown }) {
                    Icon(imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Show menu")
                    DropdownMenu(
                        expanded = dropDown,
                        onDismissRequest = { dropDown = false }
                    ) {
                        DropdownMenuItem(onClick = { dropDown = false }
                        ) {
                            Icon(imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite")
                            Text(text = " Favorites")
                        }
                    }
                }
            }
        )
        MovieList()
    }
}