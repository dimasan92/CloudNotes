package ru.dimasan92.cloudnotes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dimasan92.cloudnotes.data.Repository

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        viewStateLiveData.value = MainViewState(Repository.notes)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}