package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.KeyboardViewModel

@Composable
fun CustomizerScreen(viewModel: KeyboardViewModel) {
    var themeName by remember { mutableStateOf("ثيم النيون الخاص بي") }
    var selectedPrimary by remember { mutableStateOf("#00F0FF") }
    var selectedBg by remember { mutableStateOf("#0A0E1A") }
    var selectedKey by remember { mutableStateOf("#161B2E") }
    var savedMessage by remember { mutableStateOf(false) }

    val primaryColors = listOf("#00F0FF", "#FF007F", "#00FF66", "#FFB6C1", "#FFD700", "#A855F7")
    val bgColors = listOf("#0A0E1A", "#140A1A", "#051610", "#1A1015", "#0F0F1A", "#121212")
    val keyColors = listOf("#161B2E", "#261530", "#0D2B20", "#301C25", "#242438", "#1E1E2E")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "استوديو الثيمات المخصص",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "صمم وصنع مظهر الكيبورد الخاص بك بكل احترافية",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = themeName,
            onValueChange = { themeName = it },
            label = { Text("اسم الثيم المخصص") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        // Primary Color Selector
        Text("لون إضاءة النيون (Accent)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            primaryColors.forEach { hex ->
                val color = Color(android.graphics.Color.parseColor(hex))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selectedPrimary == hex) 3.dp else 0.dp,
                            color = Color.White,
                            shape = CircleShape
                        )
                        .clickable { selectedPrimary = hex }
                )
            }
        }

        // Background Color Selector
        Text("خلفية الكيبورد", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            bgColors.forEach { hex ->
                val color = Color(android.graphics.Color.parseColor(hex))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selectedBg == hex) 3.dp else 0.dp,
                            color = Color.Cyan,
                            shape = CircleShape
                        )
                        .clickable { selectedBg = hex }
                )
            }
        }

        // Key Surface Color Selector
        Text("لون المفاتيح", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            keyColors.forEach { hex ->
                val color = Color(android.graphics.Color.parseColor(hex))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selectedKey == hex) 3.dp else 0.dp,
                            color = Color.Magenta,
                            shape = CircleShape
                        )
                        .clickable { selectedKey = hex }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.saveCustomTheme(
                    name = themeName,
                    primaryHex = selectedPrimary,
                    bgHex = selectedBg,
                    keyHex = selectedKey,
                    glowHex = selectedPrimary
                )
                savedMessage = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "حفظ وتطبيق الثيم المخصص",
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        if (savedMessage) {
            Text(
                text = "✨ تم حفظ وتطبيق الثيم المخصص بنجاح!",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}
