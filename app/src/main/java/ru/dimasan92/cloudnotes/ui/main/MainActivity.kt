package ru.dimasan92.cloudnotes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.ui.note.NoteActivity
import ru.dimasan92.cloudnotes.utils.view.LayoutUtils
import ru.dimasan92.cloudnotes.utils.view.LayoutUtils.Type
import ru.dimasan92.cloudnotes.utils.view.LayoutUtilsImpl

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    private lateinit var layoutUtils: LayoutUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        layoutUtils = LayoutUtilsImpl(this.applicationContext)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainRecycler.layoutManager = layoutUtils.getAdjustedLayoutManager(Type.STAGGERED)
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
            override fun invoke(note: Note) {
                openNoteScreen(note)
            }
        })
        mainRecycler.adapter = adapter

        viewModel.viewState().observe(this, Observer { t -> t?.let { adapter.notes = it.notes } })

        fab.setOnClickListener { openNoteScreen(null) }
    }

    private fun openNoteScreen(note: Note?) {
        startActivity(NoteActivity.getStartIntent(this, note))
    }
}
