package com.stibbons.qqc_compose

import android.app.Application
import com.stibbons.qqc_compose.data.RepoImpl
import com.stibbons.qqc_compose.data.Repository
import com.stibbons.qqc_compose.data.SomeService
import com.stibbons.qqc_compose.domain.FetchData
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class QqcApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(appModule)
        }
    }
}


val appModule = module {
    single { SomeService() }
    single<Repository> { RepoImpl(get()) }
    factory { FetchData(get()) }
}
