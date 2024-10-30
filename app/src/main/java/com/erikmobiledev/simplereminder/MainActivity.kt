package com.erikmobiledev.simplereminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.erikmobiledev.simplereminder.navigation.NavManager
import com.erikmobiledev.simplereminder.ui.theme.TaskappTheme
import com.erikmobiledev.simplereminder.viewModels.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tasksViewModel: TasksViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            TaskappTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavManager(tasksViewModel)
                }
            }
        }
    }
}