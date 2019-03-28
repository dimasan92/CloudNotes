package ru.dimasan92.cloudnotes.data.provider

import androidx.lifecycle.LiveData
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.model.Result
import ru.dimasan92.cloudnotes.data.model.User

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<Result>
    fun getNoteById(id: String): LiveData<Result>
    fun saveNote(note: Note): LiveData<Result>
    fun deleteNote(noteId: String): LiveData<Result>
    fun getCurrentUser(): LiveData<User?>
}