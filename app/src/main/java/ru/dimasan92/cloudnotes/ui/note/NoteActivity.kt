package ru.dimasan92.cloudnotes.ui.note

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Color
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.extensions.format
import ru.dimasan92.cloudnotes.extensions.getColorInt
import ru.dimasan92.cloudnotes.ui.base.BaseActivity
import ru.dimasan92.cloudnotes.ui.note.NoteViewState.Data
import java.util.*

private const val SAVE_DELAY = 2000L

class NoteActivity : BaseActivity<Data, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = "${NoteActivity::class.java.name}extra.NOTE"

        fun start(context: Context, noteId: String?) =
            context.startActivity<NoteActivity>(EXTRA_NOTE to noteId)
    }

    override val viewModel: NoteViewModel by viewModel()
    override val layoutRes = R.layout.activity_note
    private var note: Note? = null
    private var color: Color = Color.WHITE
    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            triggerSaveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

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
        colorPicker.onColorClickListener = {
            color = it
            setToolbarColor(it)
            triggerSaveNote()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) =
        menuInflater.inflate(R.menu.note_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> super.onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (colorPicker.isOpen) {
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.delete_dialog_message
            negativeButton(R.string.cancel_btn_title) { it.dismiss() }
            positiveButton(R.string.ok_btn_title) { viewModel.deleteNote() }
        }.show()
    }

    override fun renderData(data: Data) {
        if (data.isDeleted) finish()

        note = data.note
        data.note?.let { color = it.color }
        initView()
    }

    override fun showLoading() {
        setVisibility(contentVisibility = View.GONE, progressVisibility = View.VISIBLE)
    }

    override fun hideLoading() {
        setVisibility(contentVisibility = View.VISIBLE, progressVisibility = View.GONE)
    }

    private fun setVisibility(contentVisibility: Int, progressVisibility: Int) {
        contentLayout.visibility = contentVisibility
        progressBar.visibility = progressVisibility
    }

    private fun initView() {
        note?.run {
            supportActionBar?.title = lastChanged.format()
            toolbar.setBackgroundColor(color.getColorInt(this@NoteActivity))

            removeEditListener()
            titleEt.setText(title)
            bodyEt.setText(body)
            setEditListener()
        }
    }

    private fun setEditListener() {
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }

    private fun removeEditListener() {
        titleEt.removeTextChangedListener(textChangeListener)
        bodyEt.removeTextChangedListener(textChangeListener)
    }

    private fun triggerSaveNote() {
        if (titleEt.text!!.length < 3 && bodyEt.text.length < 3) return

        Handler().postDelayed(
            {
                note = note?.copy(
                    title = titleEt.text.toString(),
                    body = bodyEt.text.toString(),
                    lastChanged = Date(),
                    color = color
                ) ?: createNewNote()
                note?.let { viewModel.saveChanges(it) }
            }, SAVE_DELAY
        )
    }

    private fun createNewNote() =
        Note(UUID.randomUUID().toString(), titleEt.text.toString(), bodyEt.text.toString())

    private fun setToolbarColor(color: Color) {
        toolbar.setBackgroundColor(color.getColorInt(this))
    }
}