package ru.dimasan92.cloudnotes.ui.note

import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) :
    BaseViewState<NoteViewState.Data>(data, error) {

    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}