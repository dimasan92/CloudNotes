package ru.dimasan92.cloudnotes.ui.main

import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.ui.base.BaseViewState

class MainViewState(notes: List<Note>? = null,error: Throwable? = null):
        BaseViewState<List<Note>?>(notes, error)