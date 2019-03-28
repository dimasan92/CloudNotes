package ru.dimasan92.cloudnotes.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.dimasan92.cloudnotes.data.Repository
import ru.dimasan92.cloudnotes.data.provider.FireStoreProvider
import ru.dimasan92.cloudnotes.data.provider.RemoteDataProvider
import ru.dimasan92.cloudnotes.ui.main.MainViewModel
import ru.dimasan92.cloudnotes.ui.note.NoteViewModel
import ru.dimasan92.cloudnotes.ui.splash.SplashViewModel

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}