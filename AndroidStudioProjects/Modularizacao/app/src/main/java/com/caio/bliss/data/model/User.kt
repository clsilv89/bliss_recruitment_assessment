package com.caio.bliss.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    val login: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var avatar_url: String
) : Parcelable