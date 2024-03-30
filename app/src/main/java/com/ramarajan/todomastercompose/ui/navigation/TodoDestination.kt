package com.ramarajan.todomastercompose.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramarajan.todomastercompose.R


sealed class Route(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int,
    val routeWithoutArgs: String = route
) {
    data object TodoList : Route("todoList", R.string.app_name, R.drawable.ic_launcher_background)
    data object AddTodo : Route("addTodo", R.string.app_name, R.drawable.ic_launcher_background)

}