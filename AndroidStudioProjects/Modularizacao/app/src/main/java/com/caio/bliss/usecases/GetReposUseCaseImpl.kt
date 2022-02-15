package com.caio.bliss.usecases

import com.caio.bliss.data.model.Repo
import com.caio.bliss.data.network.ResultWrapper
import com.caio.bliss.data.repository.Repository

class GetReposUseCaseImpl(
    private val repository: Repository
) : IGetReposUseCase {
    override suspend fun invoke(org: String): ResultWrapper<List<Repo>> {
        return repository.getRepos(org)
    }
}
