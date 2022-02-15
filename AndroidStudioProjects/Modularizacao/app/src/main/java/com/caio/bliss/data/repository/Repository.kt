package com.caio.bliss.data.repository

import com.caio.bliss.data.api.Api
import com.caio.bliss.data.network.NetWorkHelp
import kotlinx.coroutines.Dispatchers

class Repository(
    private val api: Api,
    private val networkHelp: NetWorkHelp
) {
    private val dispatcher = Dispatchers.IO
    suspend fun getEmojis() =
        networkHelp.safeApiCall(dispatcher) { api.getEmojis() }

    suspend fun getUser(name: String) =
        networkHelp.safeApiCall(dispatcher) { api.getUser(name) }

    suspend fun getRepos(org: String, page: Int = 0) =
        networkHelp.safeApiCall(dispatcher) { api.getRepos(org, page) }
}
