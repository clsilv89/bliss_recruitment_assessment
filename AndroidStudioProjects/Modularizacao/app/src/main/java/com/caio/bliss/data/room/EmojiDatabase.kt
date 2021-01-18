package com.caio.bliss.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caio.bliss.data.model.Emoji

@Database(entities = [Emoji::class], version = 1)
abstract class EmojiDatabase : RoomDatabase() {
    abstract fun emojiDAO(): EmojiDAO
}