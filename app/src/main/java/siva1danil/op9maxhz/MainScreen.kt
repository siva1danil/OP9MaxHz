package siva1danil.op9maxhz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            MainScreenCard(
                icon = Icons.Default.Monitor,
                title = "Unlock",
                subtitle = if (viewModel.permission.value) "Force unlock 120 Hz for all applications." else "No WRITE_SECURE_SETTINGS permission. Run `pm grant ${viewModel.packageName} android.permission.WRITE_SECURE_SETTINGS` in adb shell to grant.",
                switchChecked = viewModel.enabled.value,
                switchEnabled = viewModel.permission.value,
                switchOnToggle = { viewModel.setEnabled(!viewModel.enabled.value) }
            )

            MainScreenCard(
                icon = Icons.Default.Settings,
                title = "Default refresh rate",
                subtitle = "Refresh rate limit when not unlocked.\nCurrent value: ${viewModel.rate.value} Hz.",
                onClick = { viewModel.setRate(if (viewModel.rate.value == 120.0f) 60.0f else 120.0f) }
            )
        }
    }
}