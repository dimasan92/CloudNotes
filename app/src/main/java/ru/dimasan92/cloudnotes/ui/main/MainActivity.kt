package ru.dimasan92.cloudnotes.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.ui.base.BaseActivity
import ru.dimasan92.cloudnotes.ui.note.NoteActivity
import ru.dimasan92.cloudnotes.utils.view.LayoutUtilsImpl

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    override val layoutRes = R.layout.activity_main
    private val adapter = MainAdapter { openNoteScreen(it) }
    private val layoutUtils by lazy { LayoutUtilsImpl(this.applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        mainRecycler.adapter = adapter
        fab.setOnClickListener { openNoteScreen(null) }
    }

    override fun renderData(data: List<Note>?) {
        data?.let { adapter.notes = it }
    }

    private fun openNoteScreen(note: Note?) {
        startActivity(NoteActivity.getStartIntent(this, note))
    }
}
