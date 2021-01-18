package com.caio.bliss.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    val id: Long,
    val node_id: String,
    val name: String,
    val full_name: String,
    val private: Boolean
) : Parcelable