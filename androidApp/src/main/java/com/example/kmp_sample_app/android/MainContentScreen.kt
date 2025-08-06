import Presentation.ContentViewModel
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmp_sample_app.android.TodosActivity
import com.example.kmp_sample_app.android.UserListActivity

@Composable
fun MainContentScreen(
    contentViewModel: ContentViewModel = ContentViewModel()
) {
    val context = LocalContext.current
    val state by contentViewModel.viewState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ContentOptionListScreen(
            contentViewModel = contentViewModel,
            onOptionSelected = { option ->
                when (option) {
                    ContentViewModel.ContentOptions.USER_LIST -> {
                        context.startActivity(
                            Intent(context, UserListActivity::class.java)
                        )
                    }

                    ContentViewModel.ContentOptions.TODOS -> {
                        context.startActivity(
                            Intent(context, TodosActivity::class.java)
                        )
                    }
                }
            }
        )

        Divider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
fun ContentOptionListScreen(
    contentViewModel: ContentViewModel,
    onOptionSelected: (ContentViewModel.ContentOptions) -> Unit
) {
    val state by contentViewModel.viewState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(state.options) { option ->
            val isSelected = option == state.selectedOption

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else Color.Transparent
                    )
                    .clickable {
                        contentViewModel.selectOption(option)
                        onOptionSelected(option)
                    }
                    .padding(12.dp)
            ) {
                Text(
                    text = option.displayName.replace("_", " "),
                    style = if (isSelected)
                        MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                    else
                        MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}