package com.caio.bliss.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caio.bliss.data.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
}