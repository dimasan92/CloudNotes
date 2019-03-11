package ru.dimasan92.cloudnotes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.dimasan92.cloudnotes.data.model.Color
import ru.dimasan92.cloudnotes.data.model.Note
import java.util.*

object Repository {

    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes = mutableListOf(
        Note(
            id = UUID.randomUUID().toString(),
            title = "Random note 1",
            body = "Two exquisite objection delighted deficient yet its contained. Cordial because are account evident its subject but eat.",
            color = Color.WHITE
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Random note 2",
            body = "Bringing unlocked me an striking ye perceive. Mr by wound hours oh happy. Me in resolution pianoforte continuing we.",
            color = Color.BLUE
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Random note 3",
            body = "Marianne shutters mr steepest to me. Up mr ignorant produced distance although is sociable blessing. Ham whom call all lain like.",
            color = Color.GREEN
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Random note 4",
            body = "She the favourable partiality inhabiting travelling impression put two.",
            color = Color.PINK
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Random note 5",
            body = "His six are entreaties instrument acceptance unsatiable her.",
            color = Color.RED
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Random note 6",
            body = "Amongst as or on herself chapter entered carried no.",
            color = Color.YELLOW
        ),
        Note(
            id = UUID.randomUUID().toString(),
            title = "Random note 7",
            body = "Luckily friends do ashamed to do suppose. Tried meant mr smile so. Exquisite behaviour as to middleton perfectly.",
            color = Color.VIOLET
        )
    )

    init {
        notesLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>> = notesLiveData

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        notes.forEachIndexed { i, n ->
            if (n == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}