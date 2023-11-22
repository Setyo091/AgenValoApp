package com.example.submissioncompose.navigation

sealed class Screen(val route: String) {

    object Home: Screen("home")
    object Detail: Screen("detail/{${NavArg.MEMBER_ID.key}}") {

        fun createRoute(id: Int) = "detail/$id"
    }
    object Favorite: Screen("favorite")
    object About: Screen("about")
}
