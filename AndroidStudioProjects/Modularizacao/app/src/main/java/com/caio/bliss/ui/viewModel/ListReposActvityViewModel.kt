package com.caio.bliss.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caio.bliss.data.model.Repo
import com.caio.bliss.data.network.ResultWrapper.GenericError
import com.caio.bliss.data.network.ResultWrapper.NetworkError
import com.caio.bliss.data.network.ResultWrapper.Success
import com.caio.bliss.data.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListReposActvityViewModel(private val repository: Repository) : ViewModel() {

    private var repoResponse = MutableLiveData<List<Repo>>()
    fun repoResponse(): LiveData<List<Repo>> = repoResponse

    private var error = MutableLiveData<Boolean>().apply { value = false }
    fun error(): LiveData<Boolean> = error

    fun getMoreRepos(page: Int) = runBlocking {
        launch {
            try {
                when (val response = repository.getRepos("google", page)) {
                    is NetworkError -> error.value = true
                    is GenericError -> error.value = true
                    is Success<List<Repo>> -> repoResponse.value = response.value
                }
            } catch (e: Exception) {
                error.value
            } finally {
            }
        }
    }
}