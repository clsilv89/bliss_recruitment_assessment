package com.caio.bliss.usecases

import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.network.ResultWrapper

interface IGetEmojiUseCase {
    suspend fun invoke(): ResultWrapper<List<Emoji>>
}