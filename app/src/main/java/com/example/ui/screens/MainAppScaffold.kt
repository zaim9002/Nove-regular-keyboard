package com.example.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.viewmodel.KeyboardViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Themes : Screen("themes", "الثيمات", Icons.Default.Palette)
    object Simulator : Screen("simulator", "المحاكي", Icons.Default.Keyboard)
    object Customizer : Screen("customizer", "الاستوديو", Icons.Default.Style)
    object Emojis : Screen("emojis", "الإيموجي", Icons.Default.EmojiEmotions)
    object Settings : Screen("settings", "الإعدادات", Icons.Default.Settings)
}

@Composable
fun MainAppScaffold(viewModel: KeyboardViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Themes) }

    val items = listOf(
        Screen.Themes,
        Screen.Simulator,
        Screen.Customizer,
        Screen.Emojis,
        Screen.Settings
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                Screen.Themes -> ThemesScreen(viewModel, onNavigateToSimulator = { currentScreen = Screen.Simulator })
                Screen.Simulator -> SimulatorScreen(viewModel)
                Screen.Customizer -> CustomizerScreen(viewModel)
                Screen.Emojis -> EmojisStickersScreen(viewModel)
                Screen.Settings -> SettingsScreen(viewModel)
            }
        }
    }
}
