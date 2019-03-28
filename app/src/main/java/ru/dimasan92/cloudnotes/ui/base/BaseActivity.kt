package ru.dimasan92.cloudnotes.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.errors.NoAuthException

private const val RC_SIGN_IN = 458

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        showLoading()
        viewModel.getViewState().observe(this, Observer { viewState ->
            viewState?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }
                hideLoading()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

    protected open fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLoginActivity()
            else -> error.message?.let { showError(it) }
        }
    }

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    abstract fun renderData(data: T)
    abstract fun showLoading()
    abstract fun hideLoading()


    protected fun showError(error: String) {
        Snackbar.make(mainRecycler, error, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.ok_btn_title) { dismiss() }
            show()
        }
    }
}