package com.caio.bliss.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caio.bliss.R
import com.caio.bliss.data.repository.Repository
import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.network.ResultWrapper.GenericError
import com.caio.bliss.data.network.ResultWrapper.NetworkError
import com.caio.bliss.data.network.ResultWrapper.Success
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListEmojiViewModel(private val repository: Repository) {
    private var emojiResponse = MutableLiveData<List<Emoji>>()
    fun emojiResponse(): LiveData<List<Emoji>> = emojiResponse

    private var error = MutableLiveData<Boolean>().apply { value = false }
    fun error(): LiveData<Boolean> = error

    private val statusLabel = MutableLiveData<Int>()
    fun statusLabel(): LiveData<Int> = statusLabel

    fun getEmojis() = runBlocking {
        launch {
            try {
                when (val response = repository.getEmojis()) {
                    is NetworkError -> statusLabel.value = R.string.main_load_content_error
                    is GenericError -> statusLabel.value = R.string.main_load_content_error
                    is Success<List<Emoji>> -> emojiResponse.value = response.value
                }
            } catch (e: Exception) {
                error.value
            }
        }
    }
}