package com.erikmobiledev.simplereminder.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erikmobiledev.cronoapp.R
import com.erikmobiledev.simplereminder.components.FloatButton
import com.erikmobiledev.simplereminder.components.MainTitle
import com.erikmobiledev.simplereminder.model.Tasks
import com.erikmobiledev.simplereminder.viewModels.TasksViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, tasksViewModel: TasksViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = stringResource(R.string.recordatorios)) },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatButton {
                navController.navigate("AddView")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        ContentHomeView(it, navController, tasksViewModel)
    }
}

@Composable
fun ContentHomeView(it: PaddingValues, navController: NavController, tasksViewModel: TasksViewModel) {
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
    ) {
        val taskList = tasksViewModel.tasksList.collectAsState().value // Accede al valor de la lista de tareas

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskList) { item -> // Recorre los elementos de la lista
                val deleteTask = SwipeAction(
                    icon = rememberVectorPainter(Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = { tasksViewModel.deleteTask(item) }
                )

                SwipeableActionsBox(
                    endActions = listOf(deleteTask)
                ) {
                    TaskItemCard(item)
                }
            }
        }
    }
}

@Composable
fun TaskItemCard(task: Tasks) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = when {
                    task.date.isNotEmpty() && task.time.isNotEmpty() -> "${task.date} - ${task.time}"
                    task.date.isNotEmpty() -> task.date
                    task.time.isNotEmpty() -> task.time
                    else -> ""
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
