package com.caio.bliss.usecases

import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.network.ResultWrapper
import com.caio.bliss.data.repository.Repository

class GetEmojiUseCaseImpl(
    private val repository: Repository
) : IGetEmojiUseCase {
    override suspend fun invoke(): ResultWrapper<List<Emoji>> {
        return repository.getEmojis()
    }
}