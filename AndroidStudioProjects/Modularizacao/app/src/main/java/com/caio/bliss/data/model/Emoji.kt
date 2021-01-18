package com.caio.bliss.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Emoji (
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val url: String
) : Parcelable
