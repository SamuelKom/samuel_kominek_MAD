package com.example.movie_app.screens

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movie_app.R
import com.example.testapp.models.Movie
import com.example.testapp.models.getMovies


@Composable
fun HomeScreen(navController: NavController) {
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
                        DropdownMenuItem(onClick = { navController.navigate(route = Screen.Favorite.route) }
                        ) {
                            Icon(imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite")
                            Text(text = " Favorites")
                        }
                    }
                }
            }
        )
        MovieList(navController = navController)
    }
}


@Composable
fun MovieList(navController: NavController){
    val list : List<Movie> = getMovies()
    LazyColumn {
        items(list) {
            MovieRow(movie = it) { movieId -> navController.navigate(route = Screen.Detail.passMovieId(movieId))}
        }
    }
}


@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {}) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column {
            Box(modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
            ) {
                BuildImage(imageURL = movie.images[0])

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
            BuildDetails(movie = movie)
        }
    }
}


@Composable
fun BuildImage(imageURL: String){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.loading),
        contentDescription = "Image",
        contentScale = ContentScale.Crop
    )
}


@Composable
fun BuildDetails(movie: Movie){
    var icon by remember { mutableStateOf(Icons.Default.KeyboardArrowUp) }
    var visibility by remember { mutableStateOf(false) }

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
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
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


@Composable
fun SimpleAppBar(title: String, navController: NavController){
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        backgroundColor = colorResource(id = R.color.purple_700),
        contentColor = Color.White,
        elevation = 12.dp,
        navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, "backIcon")
        }}
    )
}