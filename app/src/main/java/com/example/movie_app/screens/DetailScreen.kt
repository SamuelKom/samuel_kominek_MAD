package com.example.movie_app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movie_app.models.Movie
import com.example.movie_app.viewModel.MoviesViewModel
import com.example.movie_app.widgets.HorizontalScrollableImageView
import com.example.movie_app.widgets.MovieRow
import com.example.movie_app.widgets.SimpleTopAppBar

@Composable
fun DetailScreen(
    navController: NavController, moviesViewModel: MoviesViewModel, movieId: String?
) {

    movieId?.let {
        val movie = moviesViewModel.movieList.filter { it.id == movieId }[0]

        // needed for show/hide snackbar
        val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

        Scaffold(
            scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
            topBar = {
                SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                    Text(text = movie.title)
                }
            },
        ) { padding ->
            MainContent(Modifier.padding(padding), movie, moviesViewModel)
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, movie: Movie, moviesViewModel: MoviesViewModel) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MovieRow(movie = movie, onFavClick = { movieId ->
                moviesViewModel.markFavorite(movieId)
            })

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Text(text = "Movie Images", style = MaterialTheme.typography.h5)

            HorizontalScrollableImageView(movie = movie)
        }
    }
}