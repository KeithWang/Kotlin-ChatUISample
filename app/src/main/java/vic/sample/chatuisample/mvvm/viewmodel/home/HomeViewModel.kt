package vic.sample.chatuisample.mvvm.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vic.sample.chatuisample.R
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import vic.sample.chatuisample.mvvm.model.login.LoginRepository
import vic.sample.chatuisample.mvvm.viewmodel.BasicViewModel
import vic.sample.chatuisample.mvvm.viewmodel.home.item.HomeViewData
import vic.sample.chatuisample.mvvm.viewmodel.home.item.LogoutResult

class HomeViewModel(private val loginRepository: LoginRepository) : BasicViewModel() {

    private val logoutResult = MutableLiveData<LogoutResult>()
    private val homeViewDataResult = MutableLiveData<HomeViewData>()

    fun getLogoutResult(): LiveData<LogoutResult> {
        return logoutResult
    }

    fun getHomeViewDataResult(): LiveData<HomeViewData> {
        return homeViewDataResult
    }

    fun onLogout() {
        val logoutStatus = loginRepository.logout()

        if (logoutStatus.status == AboutLoginOrOutStatus.SUCCESS) {
            logoutResult.value = LogoutResult(success = true)
        } else {
            logoutResult.value = LogoutResult(error = R.string.home_logout_error)
        }
    }

    fun initHomeView() {
        val isLogin = loginRepository.isLoggedIn

        if (isLogin)
            homeViewDataResult.value = HomeViewData(userData = loginRepository.user)
        else
            homeViewDataResult.value = HomeViewData(isLogin = false)
    }

}