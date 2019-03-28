package ru.dimasan92.cloudnotes.data

import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.provider.FireStoreProvider
import ru.dimasan92.cloudnotes.data.provider.RemoteDataProvider

class Repository(private val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun deleteNote(noteId: String) = remoteProvider.deleteNote(noteId)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}