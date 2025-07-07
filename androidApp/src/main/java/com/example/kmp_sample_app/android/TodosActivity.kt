package com.example.kmp_sample_app.android

import Presentation.TodoViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmpsampleapp.database.Todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.kmp_sample_app.database.AppDatabase
import data.Database.DatabaseDriverFactory
import data.Repositories.TodoRepository
import org.koin.compose.getKoin

class TodosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    TodoScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen() {
    var newTodo by remember { mutableStateOf("") }
    val viewModel: TodoViewModel = getKoin().get() // using koin injection
    val state by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Todos") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = newTodo,
                onValueChange = { newTodo = it },
                label = { Text("Enter new todo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.addTodo(newTodo)
                    newTodo = ""
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(state.todos) { todo ->
                    TodoItem(todo = todo, onToggle = { viewModel.toggleTodo(todo) })
                }
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onToggle: () -> Unit) {
    val isDone = todo.is_done == 1L
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = todo.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = isDone,
            onCheckedChange = { onToggle() }
        )
    }
}
