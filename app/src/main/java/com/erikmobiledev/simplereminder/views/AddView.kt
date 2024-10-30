package com.erikmobiledev.simplereminder.views

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erikmobiledev.cronoapp.R
import com.erikmobiledev.simplereminder.components.MainIconButton
import com.erikmobiledev.simplereminder.components.MainTextField
import com.erikmobiledev.simplereminder.components.MainTitle
import com.erikmobiledev.simplereminder.model.Tasks
import com.erikmobiledev.simplereminder.viewModels.TasksViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController, tasksViewModel: TasksViewModel) {
    // Definir los estados en AddView
    val textState = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf("") }
    val selectedTime = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = stringResource(R.string.agregar_tarea)) },
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color.White
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    tasksViewModel.addTask(
                        Tasks(
                            title = textState.value,
                            date = selectedDate.value,
                            time = selectedTime.value
                        )
                    )
                    navController.popBackStack()
                },
                containerColor = MaterialTheme.colorScheme.primary

            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.guardar_tarea)
                )
            }
        }
    ) {
        ContentAddView(it, textState, selectedDate, selectedTime)
    }
}

@Composable
fun ContentAddView(
    it: PaddingValues,
    textState: MutableState<String>,
    selectedDate: MutableState<String>,
    selectedTime: MutableState<String>

) {
    // Variables para almacenar la fecha y hora seleccionadas
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        MainTextField(
            textState.value,
            { newText -> textState.value = newText },
            stringResource(R.string.description)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            // Selector de fecha
            Button(onClick = {
                val calendar = Calendar.getInstance()
                android.app.DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        selectedDate.value = "$dayOfMonth/${month + 1}/$year"
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.selecciona_la_fecha),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (selectedDate.value.isNotEmpty()) selectedDate.value else stringResource(
                            R.string.selecciona_la_fecha
                        ),
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            // Selector de hora
            Button(onClick = {
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    context, { _, hourOfDay, minute ->
                        selectedTime.value = String.format("%02d:%02d", hourOfDay, minute)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = stringResource(R.string.selecciona_la_hora),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (selectedTime.value.isNotEmpty()) selectedTime.value else stringResource(
                            R.string.selecciona_la_hora
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }
}
