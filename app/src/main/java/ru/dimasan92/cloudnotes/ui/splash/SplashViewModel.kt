package ru.dimasan92.cloudnotes.ui.splash

import ru.dimasan92.cloudnotes.data.Repository
import ru.dimasan92.cloudnotes.data.errors.NoAuthException
import ru.dimasan92.cloudnotes.ui.base.BaseViewModel

class SplashViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}