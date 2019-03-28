package ru.dimasan92.cloudnotes

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.dimasan92.cloudnotes.di.appModule
import ru.dimasan92.cloudnotes.di.mainModule
import ru.dimasan92.cloudnotes.di.noteModule
import ru.dimasan92.cloudnotes.di.splashModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}