package com.example.movie_app.screens

sealed class Screen(val route: String) {
    object Home: Screen(route = "home")
    object Detail: Screen(route = "detail/{movieId}"){
        fun passMovieId(movieId: String): String{
            return "detail/$movieId"
        }
    }
    object Favorite: Screen(route = "favorite")
}
