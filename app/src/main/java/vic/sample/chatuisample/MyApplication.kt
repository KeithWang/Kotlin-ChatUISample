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
import vic.sample.chatuisample.mvvm.rxprovide.AppSchedulerProvider
import vic.sample.chatuisample.mvvm.rxprovide.SchedulerProvider
import vic.sample.chatuisample.mvvm.viewmodel.home.HomeViewModel
import vic.sample.chatuisample.mvvm.viewmodel.login.LoginViewModel
import vic.sample.chatuisample.utility.Tools


class MyApplication : Application() {

    private var listOfModules = module {

        /*
        * Normal Module
        * */
        single { CacheRepository(get()) }
        single { Tools() }
        single<SchedulerProvider> { AppSchedulerProvider() }

        /*
        * Repository Module
        * */
        single { LoginOrLogoutDataSource(get()) }
        single { LoginRepository(get()) }

        /*
        * View model module
        * */
        viewModel { LoginViewModel(loginRepository = get(), tools = get()) }
        viewModel { HomeViewModel(loginRepository = get()) }
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