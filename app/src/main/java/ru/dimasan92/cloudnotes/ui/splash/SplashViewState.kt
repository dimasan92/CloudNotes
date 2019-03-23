package ru.dimasan92.cloudnotes.ui.splash

import ru.dimasan92.cloudnotes.ui.base.BaseViewState

class SplashViewState(isAuth: Boolean? = null, error: Throwable? = null) :
    BaseViewState<Boolean?>(isAuth, error)