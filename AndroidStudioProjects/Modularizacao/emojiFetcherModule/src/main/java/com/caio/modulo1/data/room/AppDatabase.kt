package com.caio.modulo1.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caio.modulo1.data.model.Emoji

@Database(entities = [Emoji::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emojiDAO(): EmojiDAO
}