package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeDao {
    @Query("SELECT * FROM custom_themes")
    fun getAllThemes(): Flow<List<KeyboardThemeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(theme: KeyboardThemeEntity): Long

    @Update
    suspend fun updateTheme(theme: KeyboardThemeEntity)

    @Delete
    suspend fun deleteTheme(theme: KeyboardThemeEntity)

    @Query("UPDATE custom_themes SET isApplied = (id = :themeId)")
    suspend fun setAppliedTheme(themeId: Long)
}
