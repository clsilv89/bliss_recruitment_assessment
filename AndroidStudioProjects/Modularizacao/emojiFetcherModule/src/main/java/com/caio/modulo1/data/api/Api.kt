package com.caio.modulo1.data.api

import com.caio.modulo1.data.model.Emoji
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    companion object {
        private const val EMOJIS = "/emojis"
    }

    @GET(EMOJIS)
    fun getEmojis() : Call<List<Emoji>>
}