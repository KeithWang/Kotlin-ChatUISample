package vic.sample.chatuisample.mvvm.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import vic.sample.chatuisample.R
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import vic.sample.chatuisample.mvvm.model.login.LoginRepository
import vic.sample.chatuisample.mvvm.viewmodel.BasicViewModel
import vic.sample.chatuisample.mvvm.viewmodel.home.item.HomeViewData
import vic.sample.chatuisample.mvvm.viewmodel.home.item.LogoutResult

class HomeViewModel(
    coroutineScopeProvider: CoroutineScope? = null,
    private val loginRepository: LoginRepository
) : BasicViewModel() {

    private val coroutineScope = getViewModelScope(coroutineScopeProvider)

    private val isLoading = MutableLiveData<Boolean>()
    private val logoutResult = MutableLiveData<LogoutResult>()
    private val homeViewDataResult = MutableLiveData<HomeViewData>()

    fun getIsLoadingStatus(): LiveData<Boolean> {
        return isLoading
    }

    fun getLogoutResult(): LiveData<LogoutResult> {
        return logoutResult
    }

    fun getHomeViewDataResult(): LiveData<HomeViewData> {
        return homeViewDataResult
    }

    fun onLogout() {
        coroutineScope.launch(Main) {
            isLoading.value = true

            val logoutStatus = loginRepository.logout()

            if (logoutStatus.status == AboutLoginOrOutStatus.SUCCESS) {
                logoutResult.value = LogoutResult(success = true)
            } else {
                logoutResult.value = LogoutResult(error = R.string.home_logout_error)
            }

            isLoading.value = false
        }

    }

    fun onInitHomeView() {
        /*
        * It can custom the pass data
        * */
        val userObj = loginRepository.user
        val isLogin = loginRepository.isLoggedIn

        homeViewDataResult.value = HomeViewData(isLogin = isLogin, userData = userObj)

    }

}