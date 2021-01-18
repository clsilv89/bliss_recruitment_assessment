package com.caio.bliss.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caio.bliss.data.model.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun all(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg user: User)

    @Query("DELETE FROM user WHERE id = :id")
    fun delete(id: Long)
}