package com.caio.bliss.usecases

import com.caio.bliss.data.model.User
import com.caio.bliss.data.network.ResultWrapper

interface IGetUserUseCase {
    suspend fun invoke(name: String): ResultWrapper<User>
}
