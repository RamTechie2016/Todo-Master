package com.ramarajan.todomastercompose.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.ramarajan.todomastercompose.R
import com.ramarajan.todomastercompose.ui.screens.AddTodoScreen
import com.ramarajan.todomastercompose.ui.screens.TodoListScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = Route.TodoList

    TodoNavHost(
        navController = navController
    )

}


@Composable
private fun TodoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.TodoList.route,
        modifier = modifier
    ) {
        composable(
            route = Route.TodoList.route,
        ) {

            TodoListScreen{ todoId ->
               navController.navigate(Route.AddTodo.route)
            }

        }

        composable(
            route = Route.AddTodo.route

        ) {
//            val todoId = it.arguments?.getInt("todoId")
            AddTodoScreen(todoId = 1, onAddItem = { st1,st->

            }, onToggleCompleted = {

            })
//            if (todoId != null) {
//                NavigationUtils.navigateToAddTodoScreen(todoId = todoId,navController)
//            }


        }

    }
}
