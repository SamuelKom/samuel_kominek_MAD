package com.example.movie_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movie_app.screens.DetailScreen
import com.example.movie_app.screens.FavoriteScreen
import com.example.movie_app.screens.HomeScreen
import com.example.movie_app.screens.Screen

@Composable
fun MovieNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(route = Screen.Home.route) { HomeScreen(navController = navController) }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("movieId") {
            type = NavType.StringType
            })
        ) {backStackEntry ->
            DetailScreen(navController, movieId = backStackEntry.arguments?.getString("movieId"))
        }
        composable(route = Screen.Favorite.route) { FavoriteScreen(navController = navController) }
    }
}

