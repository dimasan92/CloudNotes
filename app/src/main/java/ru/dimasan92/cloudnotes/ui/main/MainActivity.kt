package ru.dimasan92.cloudnotes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Note
import ru.dimasan92.cloudnotes.ui.base.BaseActivity
import ru.dimasan92.cloudnotes.ui.note.NoteActivity
import ru.dimasan92.cloudnotes.ui.splash.SplashActivity
import ru.dimasan92.cloudnotes.utils.view.LayoutUtils
import ru.dimasan92.cloudnotes.utils.view.LayoutUtilsImpl

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override val viewModel: MainViewModel by viewModel()
    override val layoutRes = R.layout.activity_main
    private val adapter = MainAdapter { openNoteScreen(it) }
    private val layoutUtils by lazy { LayoutUtilsImpl(this.applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        mainRecycler.layoutManager =
            layoutUtils.getAdjustedLayoutManager(LayoutUtils.Type.STAGGERED)
        mainRecycler.adapter = adapter
        fab.setOnClickListener { openNoteScreen(null) }
    }

    override fun onCreateOptionsMenu(menu: Menu?) =
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> false
        }

    override fun renderData(data: List<Note>?) {
        data?.let { adapter.notes = it }
    }

    override fun showLoading() {
        setVisibility(contentVisibility = View.GONE, progressVisibility = View.VISIBLE)
    }

    override fun hideLoading() {
        setVisibility(contentVisibility = View.VISIBLE, progressVisibility = View.GONE)
    }

    private fun setVisibility(contentVisibility: Int, progressVisibility: Int) {
        mainRecycler.visibility = contentVisibility
        fab.visibility = contentVisibility
        progressBar.visibility = progressVisibility
    }

    private fun openNoteScreen(note: Note?) {
        NoteActivity.start(this, note?.id)
    }

    private fun showLogoutDialog() {
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.ok_btn_title) { onLogout() }
            negativeButton(R.string.cancel_btn_title) { it.dismiss() }
        }.show()
    }

    private fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }
}
