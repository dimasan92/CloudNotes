package ru.dimasan92.cloudnotes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dimasan92.cloudnotes.data.Repository

class MainViewModel(repository: Repository = Repository) : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        repository.getNotes().observeForever {
            viewStateLiveData.value =
                viewStateLiveData.value?.copy(notes = it!!) ?: MainViewState(it!!)
        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}