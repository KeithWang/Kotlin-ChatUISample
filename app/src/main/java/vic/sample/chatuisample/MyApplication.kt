package vic.sample.chatuisample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import vic.sample.chatuisample.mvvm.model.cache.CacheRepository
import vic.sample.chatuisample.mvvm.model.simulate.LoginOrLogoutDataSource
import vic.sample.chatuisample.mvvm.model.login.LoginRepository
import vic.sample.chatuisample.mvvm.rxprovider.AppSchedulerProvider
import vic.sample.chatuisample.mvvm.rxprovider.SchedulerProvider
import vic.sample.chatuisample.mvvm.viewmodel.home.HomeViewModel
import vic.sample.chatuisample.mvvm.viewmodel.login.LoginViewModel


class MyApplication : Application() {

    private var listOfModules = module {

        /*
        * Normal Module
        * */
        single { CacheRepository(get()) }
        single<SchedulerProvider> { AppSchedulerProvider() }

        /*
        * Repository Module
        * */
        single { LoginOrLogoutDataSource(get()) }
        single { LoginRepository(get()) }

        /*
        * View model module
        * */
        viewModel { LoginViewModel(get()) }
        viewModel { HomeViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOfModules)
        }
    }
}