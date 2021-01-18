package com.caio.bliss.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caio.bliss.data.model.Emoji

@Dao
interface EmojiDAO {
    @Query("SELECT * FROM emoji")
    fun all(): List<Emoji>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg emoji: Emoji)
}