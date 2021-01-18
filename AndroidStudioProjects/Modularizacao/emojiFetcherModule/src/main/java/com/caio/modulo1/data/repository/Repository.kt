package com.caio.modulo1.data.repository

import com.caio.modulo1.data.api.Api
import com.caio.modulo1.data.model.Emoji
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val api: Api) {
    suspend fun getEmojis(): List<Emoji> =
        withContext(Dispatchers.IO) {
            api.getEmojis().execute().body().orEmpty()
        }
}