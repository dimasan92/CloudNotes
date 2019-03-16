package ru.dimasan92.cloudnotes.ui.note

import ru.dimasan92.cloudnotes.data.Repository
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.model.NoteResult
import ru.dimasan92.cloudnotes.ui.base.BaseViewModel

class NoteViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let { repository.saveNote(it) }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever {
            it ?: return@observeForever
            when (it) {
                is NoteResult.Success<*> -> viewStateLiveData.value =
                    NoteViewState(note = it.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
            }
        }
    }
}