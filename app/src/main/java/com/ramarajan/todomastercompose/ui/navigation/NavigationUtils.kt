package com.ramarajan.todomastercompose.ui.navigation

import androidx.navigation.NavHostController

object NavigationUtils {
    fun navigateToAddTodoScreen(
        todoId: String,
        navController: NavHostController
    ) {
        navController.navigate(Route.AddTodo.routeWithoutArgs + "/${todoId}/") {
            launchSingleTop = true
        }
    }
}