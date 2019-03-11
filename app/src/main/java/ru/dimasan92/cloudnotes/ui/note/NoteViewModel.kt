package ru.dimasan92.cloudnotes.ui.note

import androidx.lifecycle.ViewModel
import ru.dimasan92.cloudnotes.data.Repository
import ru.dimasan92.cloudnotes.data.model.Note

class NoteViewModel(private val repository: Repository = Repository) : ViewModel() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if(pendingNote!= null) {
            repository.saveNote(pendingNote!!)
        }
    }
}