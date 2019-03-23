package ru.dimasan92.cloudnotes.data

import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.provider.FireStoreProvider
import ru.dimasan92.cloudnotes.data.provider.RemoteDataProvider

object Repository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}