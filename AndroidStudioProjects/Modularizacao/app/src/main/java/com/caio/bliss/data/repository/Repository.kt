package com.caio.bliss.data.repository

import com.caio.bliss.data.api.Api
import com.caio.bliss.data.network.NetWorkHelp
import kotlinx.coroutines.Dispatchers

class Repository(private val api: Api) {

    private val dispatcher = Dispatchers.IO

    suspend fun getEmojis() =
        NetWorkHelp.safeApiCall(dispatcher) { api.getEmojis() }

    suspend fun getUser(name: String) =
        NetWorkHelp.safeApiCall(dispatcher) { api.getUser(name) }

    suspend fun getRepos(org: String) =
        NetWorkHelp.safeApiCall(dispatcher) { api.getRepos(org) }

    suspend fun getRepos(org: String, page: Int) =
        NetWorkHelp.safeApiCall(dispatcher) { api.getRepos(org, page) }
}