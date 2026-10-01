package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.KeyboardViewModel
import com.example.viewmodel.PrebuiltTheme

@Composable
fun ThemesScreen(viewModel: KeyboardViewModel, onNavigateToSimulator: () -> Unit) {
    val currentThemeId by viewModel.currentThemeId.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ثيمات كيبورد نوفا",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "اختر ثيم نيون رائع أو قم بتخصيص ثيمك الخاص",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            FloatingActionButton(
                onClick = onNavigateToSimulator,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Palette, contentDescription = "تجربة الكيبورد")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Featured Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF00F0FF), Color(0xFF9D00FF), Color(0xFFFF007F))
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "الثيم المميز من Stitch Nova",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "سايبر نيون المتوهج 2.0",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "حواف مفاتيح مضيئة تفاعلية بتأثيرات النيون المذهلة.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "جميع الثيمات المتاحة",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Themes Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.prebuiltThemes) { theme ->
                ThemeCard(
                    theme = theme,
                    isApplied = theme.id == currentThemeId,
                    onSelect = { viewModel.applyPrebuiltTheme(theme.id) }
                )
            }
        }
    }
}

@Composable
fun ThemeCard(
    theme: PrebuiltTheme,
    isApplied: Boolean,
    onSelect: () -> Unit
) {
    val primaryColor = Color(android.graphics.Color.parseColor(theme.primaryColorHex))
    val bgColor = Color(android.graphics.Color.parseColor(theme.backgroundColorHex))
    val keyColor = Color(android.graphics.Color.parseColor(theme.keyColorHex))

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onSelect)
            .border(
                width = if (isApplied) 2.5.dp else 1.dp,
                color = if (isApplied) primaryColor else Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = primaryColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = theme.badge,
                        color = primaryColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                if (isApplied) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "مفعل",
                        tint = primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Mini keyboard preview rows
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(5) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(keyColor)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(5) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (it == 2) primaryColor else keyColor)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = theme.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(primaryColor))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(keyColor))
                }
            }
        }
    }
}
