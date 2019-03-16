package ru.dimasan92.cloudnotes.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.dimasan92.cloudnotes.R

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewState().observe(this, Observer { viewState ->
            viewState ?: return@Observer
            viewState.data?.let { renderData(it) } ?: viewState.error?.let { renderError(it) }
        })
    }

    protected fun renderError(error: Throwable) {
        error.message?.let { showError(it) }
    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
        Snackbar.make(mainRecycler, error, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.ok_btn_title) { dismiss() }
            show()
        }
    }
}