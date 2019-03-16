package ru.dimasan92.cloudnotes.data.provider

import androidx.lifecycle.LiveData
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.model.NoteResult

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}