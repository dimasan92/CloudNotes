package ru.dimasan92.cloudnotes.ui.main

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.dimasan92.cloudnotes.R

class LogoutDialog : DialogFragment() {

    companion object {
        val TAG = "${LogoutDialog::class.java.simpleName}TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.logout_dialog_title)
                .setMessage(R.string.logout_dialog_message)
                .setPositiveButton(R.string.ok_btn_title) { _, _ ->
                    (activity as LogoutListener).onLogout()
                }.setNegativeButton(R.string.logout_dialog_cancel) { _, _ ->
                    dismiss()
                }.create()
        } ?: super.onCreateDialog(savedInstanceState)

    interface LogoutListener {
        fun onLogout()
    }
}