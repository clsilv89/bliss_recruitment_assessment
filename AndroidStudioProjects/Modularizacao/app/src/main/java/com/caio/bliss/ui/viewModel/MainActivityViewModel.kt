package com.caio.bliss.ui.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caio.bliss.R
import com.caio.bliss.application.MyApplication.Companion.userDatabase
import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.model.Repo
import com.caio.bliss.data.model.User
import com.caio.bliss.data.network.ResultWrapper.GenericError
import com.caio.bliss.data.network.ResultWrapper.NetworkError
import com.caio.bliss.data.network.ResultWrapper.Success
import com.caio.bliss.data.repository.Repository
import com.caio.bliss.util.custom.getViewId
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivityViewModel(private val repository: Repository): ViewModel() {
    private var emojiResponse = MutableLiveData<List<Emoji>>()
    fun emojiResponse(): LiveData<List<Emoji>> = emojiResponse

    private var error = MutableLiveData<Boolean>().apply { value = false }
    fun error(): LiveData<Boolean> = error

    private var randomEmoji = MutableLiveData<Emoji>()
    fun randomEmoji(): LiveData<Emoji> = randomEmoji

    private val statusLabel = MutableLiveData<Int>()
    fun statusLabel(): LiveData<Int> = statusLabel

    private var goList = MutableLiveData<Boolean>().apply { value = false }

    private var userResponse = MutableLiveData<User>()
    fun userResponse(): LiveData<User> = userResponse

    var userName = MutableLiveData<String>()
    fun goList(): LiveData<Boolean> = goList

    var emojiList = MutableLiveData<List<Emoji>>()

    private var repoResponse = MutableLiveData<List<Repo>>()
    fun repoResponse(): LiveData<List<Repo>> = repoResponse

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

    private fun getUser() = runBlocking {
        val userList = userDatabase?.userDAO()?.all()
        val filter = userList?.filter { user -> user.login == userName.value.orEmpty() }
        if(userName.value.isNullOrEmpty()) {
            statusLabel.value = R.string.status_label_search_user
        } else if (!filter.isNullOrEmpty()) {
            userResponse.postValue(filter.first())
        } else {
            launch {
                try {
                    when (val response = repository.getUser(userName.value.orEmpty())) {
                        is NetworkError -> statusLabel.value = R.string.main_load_content_error
                        is GenericError -> statusLabel.value = R.string.main_load_content_error
                        is Success<User> -> userResponse.value = response.value
                    }
                } catch (e: Exception) {
                    error.value
                }
            }
        }
    }

    private fun getRepos() = runBlocking {
        launch {
            try {
                when (val response = repository.getRepos("google")) {
                    is NetworkError -> statusLabel.value = R.string.main_load_content_error
                    is GenericError -> statusLabel.value = R.string.main_load_content_error
                    is Success<List<Repo>> -> repoResponse.value = response.value
                }
            } catch (e: Exception) {
                error.value
            }
        }
    }

    fun onClick(view: View) {
        when (view.getViewId()) {
            "emojiListBtn" -> goList.value = true
            "randomEmojiBtn" -> randomEmoji.value = emojiList.value?.random()
            "searchUserBtn" -> getUser()
            "listReposBtn" -> getRepos()
        }
    }
}