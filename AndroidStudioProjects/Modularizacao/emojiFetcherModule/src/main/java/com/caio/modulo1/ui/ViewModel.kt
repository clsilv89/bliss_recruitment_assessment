package com.caio.modulo1.ui

import android.util.Log
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caio.modulo1.R
import com.caio.modulo1.data.model.Emoji
import com.caio.modulo1.data.repository.Repository
import com.caio.modulo1.ui.recyclerview.RecyclerViewAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewModel(private val repository: Repository) {

    var loading: ObservableInt? = null

    fun init() {
        loading = ObservableInt(View.GONE)
    }

    private var response = MutableLiveData<List<Emoji>>()
    fun response(): LiveData<List<Emoji>> = response
    private var error = MutableLiveData<Boolean>().apply { value = false }
    fun error(): LiveData<Boolean> = error


    fun getEmojis() = runBlocking {
        launch {
            try {
                response.value = repository.getEmojis()
            } catch (e: Exception) {
                error.value = true
            }
        }
    }

    fun setAdapterList(emojis: List<Emoji>) {

    }

    fun getEmojisAt(index: Int?): Emoji? {
        return if (response.value != null && index != null && response.value?.size!! > index) {
            response.value?.get(index)
        } else null
    }
}