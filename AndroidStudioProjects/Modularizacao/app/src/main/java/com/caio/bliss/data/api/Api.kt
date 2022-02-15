package com.caio.bliss.data.api

import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.model.Repo
import com.caio.bliss.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET(EMOJIS)
    suspend fun
            getEmojis(): List<Emoji>

    @GET(USER)
    suspend fun getUser(
        @Path("name") name: String
    ): User

    @GET(REPOS)
    suspend fun getRepos(
        @Path("org") org: String,
        @Query("page") page: Int
    ): List<Repo>

    companion object {
        private const val EMOJIS = "/emojis"
        private const val USER = "/users/{name}"
        private const val REPOS = "/users/{org}/repos"
    }
}
