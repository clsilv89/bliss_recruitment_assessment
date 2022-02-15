package com.caio.bliss.usecases

import com.caio.bliss.data.model.User
import com.caio.bliss.data.network.ResultWrapper
import com.caio.bliss.data.repository.Repository

class GetUserUseCaseImpl(
    private val repository: Repository
): IGetUserUseCase {
    override suspend fun invoke(name: String): ResultWrapper<User> {
        return repository.getUser(name)
    }
}
