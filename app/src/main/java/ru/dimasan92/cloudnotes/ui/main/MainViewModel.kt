package ru.dimasan92.cloudnotes.ui.main

import androidx.lifecycle.Observer
import ru.dimasan92.cloudnotes.data.Repository
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.model.NoteResult
import ru.dimasan92.cloudnotes.ui.base.BaseViewModel

class MainViewModel(repository: Repository = Repository) :
    BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> {
        it ?: return@Observer
        when (it) {
            is NoteResult.Success<*> -> viewStateLiveData.value =
                MainViewState(notes = it.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
        }
    }
    private val repositoryNotes = repository.getNotes()
        .apply { observeForever(notesObserver) }

    init {
        viewStateLiveData.value = MainViewState()
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}