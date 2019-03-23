package ru.dimasan92.cloudnotes.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.ui.base.BaseActivity
import ru.dimasan92.cloudnotes.ui.main.MainActivity

private const val START_DELAY = 1000L

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_splash

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.requestUser() }, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf {it}?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}