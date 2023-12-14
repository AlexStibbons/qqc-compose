package com.stibbons.qqc_compose

import android.app.Application
import com.stibbons.qqc_compose.data.RepoImpl
import com.stibbons.qqc_compose.data.Repository
import com.stibbons.qqc_compose.data.SomeService
import com.stibbons.qqc_compose.domain.FetchData
import com.stibbons.qqc_compose.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class QqcApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            loadKoinModules(listOf(monolithModule))
        }
    }
}


val monolithModule = module {
    single { SomeService() }
    single<Repository> { RepoImpl(get()) }
    factory { FetchData(get()) }
    viewModel { MainViewModel(get()) }
}
