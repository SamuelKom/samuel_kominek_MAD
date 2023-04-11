package com.example.movie_app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movie_app.R
import com.example.movie_app.viewModel.MoviesViewModel
import com.example.movie_app.widgets.ErrorMsg
import com.example.movie_app.widgets.SimpleTopAppBar

@Composable
fun AddMovieScreen(navController: NavController, moviesViewModel: MoviesViewModel) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_movie))
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding),
            moviesViewModel = moviesViewModel,
            onButtonClick = { navController.popBackStack() })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier, moviesViewModel: MoviesViewModel, onButtonClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            OutlinedTextField(
                value = moviesViewModel.title.value,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    moviesViewModel.title.value = it
                    moviesViewModel.checkTitle()
                },
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = !moviesViewModel.validTitle.value
            )
            ErrorMsg(msg = "Title is required", state = !moviesViewModel.validTitle.value)

            OutlinedTextField(
                value = moviesViewModel.year.value,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    moviesViewModel.year.value = it
                    moviesViewModel.checkYear()
                },
                label = { Text(stringResource(R.string.enter_movie_year)) },
                isError = !moviesViewModel.validYear.value
            )
            ErrorMsg(msg = "Year is required", state = !moviesViewModel.validYear.value)

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6
            )

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp), rows = GridCells.Fixed(3)
            ) {
                items(moviesViewModel.genreItems.value) { genreItem ->
                    Chip(modifier = Modifier.padding(2.dp), colors = ChipDefaults.chipColors(
                        backgroundColor = if (genreItem.isSelected) colorResource(id = R.color.purple_200)
                        else colorResource(id = R.color.white)
                    ), onClick = {
                        moviesViewModel.genreItems.value = moviesViewModel.genreItems.value.map {
                            if (it.title == genreItem.title) {
                                genreItem.copy(isSelected = !genreItem.isSelected)
                            } else {
                                it
                            }
                        }
                        moviesViewModel.checkGenres()
                    }) {
                        Text(text = genreItem.title)
                    }
                }
            }
            ErrorMsg(msg = "One Genre is required", state = !moviesViewModel.validGenres.value)

            OutlinedTextField(
                value = moviesViewModel.director.value,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    moviesViewModel.director.value = it
                    moviesViewModel.checkDirector()
                },
                label = { Text(stringResource(R.string.enter_director)) },
                isError = !moviesViewModel.validDirector.value
            )
            ErrorMsg(msg = "Director is required", state = !moviesViewModel.validDirector.value)

            OutlinedTextField(
                value = moviesViewModel.actors.value,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    moviesViewModel.actors.value = it
                    moviesViewModel.checkActors()
                },
                label = { Text(stringResource(R.string.enter_actors)) },
                isError = !moviesViewModel.validActors.value
            )
            ErrorMsg(msg = "One Actor is required", state = !moviesViewModel.validActors.value)

            OutlinedTextField(
                value = moviesViewModel.plot.value,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = { moviesViewModel.plot.value = it },
                label = {
                    Text(
                        textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot)
                    )
                },
                isError = false
            )

            OutlinedTextField(
                value = moviesViewModel.rating.value,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    moviesViewModel.rating.value = it
                    moviesViewModel.checkRating()
                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = !moviesViewModel.validRating.value
            )
            ErrorMsg(msg = "Rating is required", state = !moviesViewModel.validRating.value)

            Button(enabled = moviesViewModel.isEnabledSaveButton.value, onClick = {
                moviesViewModel.addMovie()
                onButtonClick()
            }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}