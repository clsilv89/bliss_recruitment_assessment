package com.caio.bliss.usecases

import com.caio.bliss.data.model.Repo
import com.caio.bliss.data.network.ResultWrapper

interface IGetReposUseCase {
    suspend fun invoke(org: String): ResultWrapper<List<Repo>>
}
