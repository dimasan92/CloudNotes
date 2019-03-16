package ru.dimasan92.cloudnotes.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.data.model.NoteResult

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : RemoteDataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun saveNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.document(note.id).set(note)
                .addOnSuccessListener {
                    Log.d(TAG, "Note $note is saved")
                    value = NoteResult.Success(note)
                }.addOnFailureListener {
                    Log.d(TAG, "Error saving note $note, message: ${it.message}")
                    value = NoteResult.Error(it)
                }
        }

    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.document(id).get()
                .addOnSuccessListener {
                    value = NoteResult.Success(it.toObject(Note::class.java))
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        }

    override fun subscribeToAllNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.addSnapshotListener { snapshot, exception ->
                exception?.run {
                    value = NoteResult.Error(exception)
                } ?: snapshot?.run {
                    value = NoteResult.Success(mutableListOf<Note>().apply {
                        addAll(snapshot.map { it.toObject(Note::class.java) })
                    })
                }
            }
        }
}