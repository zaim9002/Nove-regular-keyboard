package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KeyboardPreviewView
import com.example.viewmodel.KeyboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorScreen(viewModel: KeyboardViewModel) {
    val currentThemeId by viewModel.currentThemeId.collectAsState()
    val simulatedText by viewModel.simulatedText.collectAsState()
    val currentLayout by viewModel.currentLayout.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    val theme = viewModel.prebuiltThemes.find { it.id == currentThemeId } ?: viewModel.prebuiltThemes[0]
    val primaryColor = Color(android.graphics.Color.parseColor(theme.primaryColorHex))
    val bgColor = Color(android.graphics.Color.parseColor(theme.backgroundColorHex))
    val keyColor = Color(android.graphics.Color.parseColor(theme.keyColorHex))
    val glowColor = Color(android.graphics.Color.parseColor(theme.glowColorHex))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Toolbar
        TopAppBar(
            title = {
                Column {
                    Text(text = "محاكي الكيبورد المباشر", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = "الثيم: ${theme.name} | اللغة: ${currentLayout.languageName}", fontSize = 12.sp, color = primaryColor)
                }
            },
            actions = {
                IconButton(onClick = { viewModel.clearText() }) {
                    Icon(Icons.Default.Clear, contentDescription = "مسح", tint = MaterialTheme.colorScheme.onBackground)
                }
                IconButton(onClick = { clipboardManager.setText(AnnotatedString(simulatedText)) }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = "نسخ", tint = MaterialTheme.colorScheme.onBackground)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        // Typing Output Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "اكتب أدناه لتجربة الكيبورد واختبار اللغة المختارة:",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = simulatedText.ifEmpty { "ابدأ الكتابة..." },
                    color = if (simulatedText.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Floating Send Button indicator
            FloatingActionButton(
                onClick = { viewModel.typeKey("\n") },
                containerColor = primaryColor,
                contentColor = bgColor,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(48.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = "إرسال", modifier = Modifier.size(20.dp))
            }
        }

        // Live Virtual Keyboard Simulator with Layout
        KeyboardPreviewView(
            primaryColor = primaryColor,
            backgroundColor = bgColor,
            keyColor = keyColor,
            glowColor = glowColor,
            keyboardLayout = currentLayout,
            onKeyClick = { char -> viewModel.typeKey(char) },
            onBackspace = { viewModel.backspace() }
        )
    }
}
