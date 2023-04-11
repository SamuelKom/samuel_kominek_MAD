package com.example.movie_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movie_app.viewModel.MoviesViewModel
import com.example.movie_app.widgets.MovieRow
import com.example.movie_app.widgets.SimpleTopAppBar

@Composable
fun FavoriteScreen(navController: NavController, moviesViewModel: MoviesViewModel){
    Scaffold(topBar = {
        SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
            Text(text = "My Favorite Movies")
        }
    }){ padding ->

        Column(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(moviesViewModel.getFavorites()){ movie ->
                    MovieRow(
                        movie = movie,
                        onItemClick = { movieId ->
                            navController.navigate(route = Screen.DetailScreen.withId(movieId))
                        },
                        onFavClick = { movieId ->
                            moviesViewModel.markFavorite(movieId)
                        }
                    )
                }
            }
        }
    }
}