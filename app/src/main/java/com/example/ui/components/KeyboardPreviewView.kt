package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.KeyboardLayout

@Composable
fun KeyboardPreviewView(
    primaryColor: Color,
    backgroundColor: Color,
    keyColor: Color,
    glowColor: Color,
    keyboardLayout: KeyboardLayout,
    onKeyClick: (String) -> Unit,
    onBackspace: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(backgroundColor, backgroundColor.copy(alpha = 0.95f))
                ),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(horizontal = 6.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Suggestion Bar & Language Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🌐 ${keyboardLayout.languageName}",
                color = primaryColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("توقعات ✨", "نوفا 🚀", "كيبورد 💡").forEach { suggestion ->
                    Surface(
                        onClick = { onKeyClick("$suggestion ") },
                        shape = RoundedCornerShape(16.dp),
                        color = keyColor
                    ) {
                        Text(
                            text = suggestion,
                            color = primaryColor,
                            fontSize = 12.dp.value.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Row 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally)
        ) {
            keyboardLayout.row1.forEach { char ->
                VirtualKey(text = char, keyColor = keyColor, textColor = Color.White, onClick = { onKeyClick(char) })
            }
        }

        // Row 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally)
        ) {
            keyboardLayout.row2.forEach { char ->
                VirtualKey(text = char, keyColor = keyColor, textColor = Color.White, onClick = { onKeyClick(char) })
            }
        }

        // Row 3 (with Shift / Backspace)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VirtualKeySpecial(text = "⇧", keyColor = keyColor, textColor = primaryColor, onClick = { onKeyClick(" ") })
            
            keyboardLayout.row3.forEach { char ->
                VirtualKey(text = char, keyColor = keyColor, textColor = Color.White, onClick = { onKeyClick(char) })
            }

            VirtualKeyIcon(
                keyColor = keyColor,
                tintColor = primaryColor,
                icon = Icons.AutoMirrored.Filled.Backspace,
                onClick = onBackspace
            )
        }

        // Row 4 (Space bar & numbers)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VirtualKeySpecial(text = "?123", keyColor = keyColor, textColor = primaryColor, onClick = { onKeyClick("123") })
            VirtualKeySpecial(text = "😊", keyColor = keyColor, textColor = primaryColor, onClick = { onKeyClick("😊") })
            
            // Spacebar
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(keyColor)
                    .clickable { onKeyClick(" ") },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "مسافة (Space)", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
            }

            VirtualKeySpecial(text = "🌐", keyColor = keyColor, textColor = primaryColor, onClick = { onKeyClick("") })
            
            VirtualKeyIcon(
                keyColor = primaryColor,
                tintColor = backgroundColor,
                icon = Icons.Default.KeyboardReturn,
                onClick = { onKeyClick("\n") }
            )
        }
    }
}

@Composable
fun RowScope.VirtualKey(
    text: String,
    keyColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(keyColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun VirtualKeySpecial(
    text: String,
    keyColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(keyColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun VirtualKeyIcon(
    keyColor: Color,
    tintColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(46.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(keyColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tintColor,
            modifier = Modifier.size(20.dp)
        )
    }
}
