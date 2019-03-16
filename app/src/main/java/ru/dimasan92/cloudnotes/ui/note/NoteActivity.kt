package ru.dimasan92.cloudnotes.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_note.*
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Color
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.ui.base.BaseActivity
import java.util.*

private const val SAVE_DELAY = 2000L

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

        fun getStartIntent(context: Context, noteId: String) =
            Intent(context, NoteActivity::class.java).apply { putExtra(EXTRA_NOTE, noteId) }
    }

    override val viewModel by lazy { ViewModelProviders.of(this).get(NoteViewModel::class.java) }
    override val layoutRes = R.layout.activity_note
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteId?.let { viewModel.loadNote(it) } ?: supportActionBar?.apply {
            title = getString(R.string.new_note_title)
        }

        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }

    override fun renderData(data: Note?) {
        note = data
        initView()
    }

    private fun initView() {
        note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.body)
            val color = when (it.color) {
                Color.WHITE -> R.color.color_white
                Color.VIOLET -> R.color.color_violet
                Color.YELLOW -> R.color.color_yellow
                Color.RED -> R.color.color_red
                Color.PINK -> R.color.color_pink
                Color.GREEN -> R.color.color_green
                Color.BLUE -> R.color.color_blue
            }
            toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            triggerSaveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private fun triggerSaveNote() {
        if (titleEt.text == null || titleEt.text!!.length < 3) return
        Handler().postDelayed({
            note = note?.copy(
                title = titleEt.text.toString(),
                body = bodyEt.text.toString(),
                lastChanged = Date()
            ) ?: createNewNote()

            note?.let {  viewModel.saveChanges(it)}
        }, SAVE_DELAY)
    }

    private fun createNewNote() =
        Note(UUID.randomUUID().toString(), titleEt.text.toString(), bodyEt.text.toString())
}