package ru.dimasan92.cloudnotes.ui.note

import ru.dimasan92.cloudnotes.data.Repository
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.model.Result
import ru.dimasan92.cloudnotes.ui.base.BaseViewModel
import ru.dimasan92.cloudnotes.ui.note.NoteViewState.Data

class NoteViewModel(private val repository: Repository) :
    BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val currentNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun saveChanges(note: Note) {
        viewStateLiveData.value = NoteViewState(Data(note = note))
    }

    override fun onCleared() {
        currentNote?.let { repository.saveNote(it) }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { t ->
            t?.let {
                viewStateLiveData.value = when (t) {
                    is Result.Success<*> -> NoteViewState(Data(note = t.data as? Note))
                    is Result.Error -> NoteViewState(error = t.error)
                }
            }
        }
    }

    fun deleteNote() {
        currentNote?.let {
            repository.deleteNote(it.id).observeForever { t ->
                t?.let {result ->
                    viewStateLiveData.value = when (result) {
                        is Result.Success<*> -> NoteViewState(Data(isDeleted = true))
                        is Result.Error -> NoteViewState(error = result.error)
                    }
                }
            }
        }
    }
}