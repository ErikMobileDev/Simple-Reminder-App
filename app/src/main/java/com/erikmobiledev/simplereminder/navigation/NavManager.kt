package com.erikmobiledev.simplereminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erikmobiledev.simplereminder.viewModels.TasksViewModel
import com.erikmobiledev.simplereminder.views.AddView
import com.erikmobiledev.simplereminder.views.HomeView

@Composable
fun NavManager(tasksViewModel: TasksViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeView(navController, tasksViewModel)
        }
        composable("AddView") {
            AddView(navController, tasksViewModel)
        }
    }
}