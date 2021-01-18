package com.caio.modulo1.data.room

import androidx.room.Dao
import androidx.room.Query
import com.caio.modulo1.data.model.Emoji

@Dao
interface EmojiDAO {
    @Query("SELECT * FROM emoji")
    fun all(): List<Emoji>
}