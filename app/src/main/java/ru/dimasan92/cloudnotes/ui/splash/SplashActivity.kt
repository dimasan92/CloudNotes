package ru.dimasan92.cloudnotes.ui.splash

import android.os.Handler
import org.koin.android.viewmodel.ext.android.viewModel
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.ui.base.BaseActivity
import ru.dimasan92.cloudnotes.ui.main.MainActivity

private const val START_DELAY = 1000L

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by viewModel()

    override val layoutRes = R.layout.activity_splash

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.requestUser() }, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}