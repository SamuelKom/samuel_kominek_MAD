package com.example.movie_app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testapp.models.Movie
import com.example.testapp.models.getMovies

@Composable
fun DetailScreen(navController: NavController,  movieId: String?) {

    val list : List<Movie> = getMovies()
    val movie = list.find { it.id == movieId } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SimpleAppBar(title = movie.title, navController = navController)
        MovieRow(movie = movie)

        Divider(color = Color.LightGray, modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Text(text = "Movie Images", fontSize = 30.sp)

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)){
            items(movie.images) {
                Card(modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                ) {
                    BuildImage(imageURL = it)
                }
            }
        }
    }
}