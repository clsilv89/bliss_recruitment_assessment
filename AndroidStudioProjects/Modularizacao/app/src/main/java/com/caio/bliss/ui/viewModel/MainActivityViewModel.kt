package com.caio.bliss.ui.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caio.bliss.R
import com.caio.bliss.application.MyApplication.Companion.emojiDatabase
import com.caio.bliss.application.MyApplication.Companion.userDatabase
import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.model.Repo
import com.caio.bliss.data.model.User
import com.caio.bliss.data.network.ResultWrapper.GenericError
import com.caio.bliss.data.network.ResultWrapper.NetworkError
import com.caio.bliss.data.network.ResultWrapper.Success
import com.caio.bliss.usecases.IGetEmojiUseCase
import com.caio.bliss.usecases.IGetReposUseCase
import com.caio.bliss.usecases.IGetUserUseCase
import com.caio.bliss.util.custom.getViewId
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivityViewModel(
    private val getEmojiUseCase: IGetEmojiUseCase,
    private val getUserUseCase: IGetUserUseCase,
    private val getReposUseCase: IGetReposUseCase
) : ViewModel() {
    private var _emojiResponse = MutableLiveData<List<Emoji>>()
    val emojiResponse: LiveData<List<Emoji>> get() = _emojiResponse
    private var _error = MutableLiveData<Boolean>().apply { value = false }
    val error: LiveData<Boolean> get() = _error
    private var _randomEmoji = MutableLiveData<Emoji>()
    val randomEmoji: LiveData<Emoji> get() = _randomEmoji
    private val _statusLabel = MutableLiveData<Int>()
    val statusLabel: LiveData<Int> get() = _statusLabel
    private var _goList = MutableLiveData<Boolean>().apply { value = false }
    val goList: LiveData<Boolean> get() = _goList
    private var _userResponse = MutableLiveData<User>()
    val userResponse: LiveData<User> get() = _userResponse
    private var _repoResponse = MutableLiveData<List<Repo>>()
    val repoResponse: LiveData<List<Repo>> get() = _repoResponse
    var userName = MutableLiveData<String>()
    var emojiList = MutableLiveData<List<Emoji>>()

    fun getEmojis() = runBlocking {
        launch {
            try {
                when (val response = getEmojiUseCase.invoke()) {
                    is NetworkError -> _statusLabel.value = R.string.main_load_content_error
                    is GenericError -> _statusLabel.value = R.string.main_load_content_error
                    is Success<List<Emoji>> -> _emojiResponse.value = response.value
                }
            } catch (e: Exception) {
                _error.value
            }
        }
    }

    fun getUser(filter: List<User>) = runBlocking {
        if (userName.value.isNullOrEmpty()) {
            _statusLabel.value = R.string.status_label_search_user
        } else if (!filter.isNullOrEmpty()) {
            _userResponse.postValue(filter.first())
        } else {
            launch {
                try {
                    when (val response = getUserUseCase.invoke(userName.value.orEmpty())) {
                        is NetworkError -> _statusLabel.value = R.string.main_load_content_error
                        is GenericError -> _statusLabel.value = R.string.main_load_content_error
                        is Success<User> -> _userResponse.value = response.value
                    }
                } catch (e: Exception) {
                    _error.value
                }
            }
        }
    }


    fun insertEmojiDB(emoji: Emoji) {
        emojiDatabase?.emojiDAO()?.add(emoji)
    }

    fun insertUserDB(user: User) {
        userDatabase?.userDAO()?.add(user)
    }

    fun getRepos() = runBlocking {
        launch {
            try {
                when (val response = getReposUseCase.invoke("google")) {
                    is NetworkError -> _statusLabel.value = R.string.main_load_content_error
                    is GenericError -> _statusLabel.value = R.string.main_load_content_error
                    is Success<List<Repo>> -> _repoResponse.value = response.value
                }
            } catch (e: Exception) {
                _error.value
            }
        }
    }

    fun onClick(view: View) {
        when (view.getViewId()) {
            "emojiListBtn" -> _goList.value = true
            "randomEmojiBtn" -> _randomEmoji.value = emojiList.value?.random()
            "searchUserBtn" -> {
                val userList = userDatabase?.userDAO()?.all()
                val filter = userList?.filter { user -> user.login == userName.value.orEmpty() }
                filter?.let {
                    getUser(it)
                }
            }
            "listReposBtn" -> getRepos()
        }
    }
}