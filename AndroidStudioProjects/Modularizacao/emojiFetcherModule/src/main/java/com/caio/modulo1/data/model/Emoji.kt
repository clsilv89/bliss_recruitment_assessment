package com.caio.modulo1.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Emoji (
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val url: String
)
