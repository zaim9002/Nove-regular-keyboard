package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.KeyboardViewModel

@Composable
fun EmojisStickersScreen(viewModel: KeyboardViewModel) {
    var selectedCategory by remember { mutableStateOf("ابتسامات") }

    val emojis = mapOf(
        "ابتسامات" to listOf("😀", "😂", "🔥", "✨", "🚀", "💡", "😎", "😍", "🥳", "🙌", "💯", "💖"),
        "حيوانات" to listOf("🐶", "🐱", "🦊", "🐼", "🦁", "🐯", "🐨", "🐰", "🦄", "🦋", "🦅", "🐬"),
        "قلوب" to listOf("❤️", "💖", "💙", "💚", "💛", "💜", "🖤", "🤍", "🤎", "❣️", "💞", "💓"),
        "رموز" to listOf("⚡", "⭐", "💫", "💥", "🌀", "💎", "🔮", "🎯", "👑", "🍀", "🎨", "🎵")
    )

    val currentEmojis = emojis[selectedCategory] ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "الرموز التعبيرية والملصقات",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "اضغط على أي رمز لتجربته وإضافته للنص فوراً",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            emojis.keys.forEach { cat ->
                val isSelected = cat == selectedCategory
                Surface(
                    onClick = { selectedCategory = cat },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Emoji Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(currentEmojis) { emoji ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { viewModel.typeKey(emoji) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 32.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sticker Packs Banner
        Text(
            text = "حزم ملصقات نوفا",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("⚡ حزمة نيون", "🚀 قطط سايبر", "✨ توهج سحري").forEach { pack ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier.padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = pack,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
