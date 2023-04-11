package com.example.movie_app.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movie_app.models.Genre
import com.example.movie_app.models.ListItemSelectable
import com.example.movie_app.models.Movie
import com.example.movie_app.models.getMovies

class MoviesViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    fun markFavorite(movieId: String){
        val movie = _movieList.filter { it.id == movieId}[0]
        movie.favorite = movie.favorite != true
    }

    fun getFavorites() : List<Movie> {
        return _movieList.filter { it.favorite }
    }

    private fun initAddMovie(){
        title.value = ""
        year.value = ""
        director.value = ""
        plot.value = ""
        actors.value = ""
        rating.value = ""
        genreItems = mutableStateOf(genres.map { genre ->
            ListItemSelectable(
                title = genre.toString(),
                isSelected = false
            )
        })

        validTitle.value = false
        validYear.value = false
        validDirector.value = false
        validActors.value = false
        validRating.value = false
        validGenres.value = false
    }

    private val genres = Genre.values().toList()
    var genreItems: MutableState<List<ListItemSelectable>> = mutableStateOf(genres.map { genre ->
        ListItemSelectable(
            title = genre.toString(),
            isSelected = false
        )
    })

    var title: MutableState<String> = mutableStateOf("")
    var year: MutableState<String> = mutableStateOf("")
    var director: MutableState<String> = mutableStateOf("")
    var actors: MutableState<String> = mutableStateOf("")
    var plot: MutableState<String> = mutableStateOf("")
    var rating: MutableState<String> = mutableStateOf("")
    var isEnabledSaveButton: MutableState<Boolean> = mutableStateOf(false)

    var validTitle: MutableState<Boolean> = mutableStateOf(false)
    var validYear: MutableState<Boolean> = mutableStateOf(false)
    var validGenres: MutableState<Boolean> = mutableStateOf(false)
    var validDirector: MutableState<Boolean> = mutableStateOf(false)
    var validActors: MutableState<Boolean> = mutableStateOf(false)
    var validRating: MutableState<Boolean> = mutableStateOf(false)

    private fun checkButton(){
        isEnabledSaveButton.value =
            validTitle.value
                    && validYear.value
                    && validGenres.value
                    && validDirector.value
                    && validActors.value
                    && validRating.value
    }

    fun checkTitle(){
        validTitle.value = title.value != ""
        checkButton()
    }

    fun checkYear(){
        validYear.value = year.value != ""
        checkButton()
    }

    fun checkGenres(){
        var selectedNum = 0
        genreItems.value.forEach { if(it.isSelected) selectedNum++ }
        validGenres.value = selectedNum > 0
        checkButton()
    }

    fun checkDirector(){
        validDirector.value = director.value != ""
        checkButton()
    }

    fun checkActors(){
        validActors.value = actors.value != ""
        checkButton()
    }

    fun checkRating(){
        if(rating.value == ""){
            validRating.value = false
            checkButton()
            return
        }
        val input = rating.value.toCharArray()
        var isValid = true
        for(i in input){
            if (i < 46.toChar() || i > 57.toChar() || i == 47.toChar()) {
                isValid = false
            }
        }
        if(isValid) validRating.value = rating.value.toFloat() > 0f
        checkButton()
    }

    fun addMovie(){
        val listOfGenres: MutableList<Genre> = mutableListOf()
        genreItems.value.forEach {
            if(it.isSelected) listOfGenres.add(Genre.valueOf(it.title))
        }
        val addMovie = Movie(
            title = title.value,
            year = year.value,
            director = director.value,
            actors = actors.value,
            rating = rating.value.toFloat(),
            plot = plot.value,
            id = title.value + year.value,
            genre =listOfGenres,
            images = listOf("https://tacm.com/wp-content/uploads/2018/01/no-image-available.jpeg")
        )
        _movieList.add(addMovie)
        initAddMovie()
    }
}