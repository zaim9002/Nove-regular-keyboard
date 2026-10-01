package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_themes")
data class KeyboardThemeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val primaryColorHex: String,
    val backgroundColorHex: String,
    val keyColorHex: String,
    val glowColorHex: String,
    val isApplied: Boolean = false,
    val isFavorite: Boolean = false
)
