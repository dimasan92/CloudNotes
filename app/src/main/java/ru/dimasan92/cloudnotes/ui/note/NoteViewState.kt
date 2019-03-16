package ru.dimasan92.cloudnotes.ui.note

import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error)