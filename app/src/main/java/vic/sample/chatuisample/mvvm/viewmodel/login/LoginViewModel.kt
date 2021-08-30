package vic.sample.chatuisample.mvvm.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.Dispatcher
import vic.sample.chatuisample.R
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import vic.sample.chatuisample.mvvm.model.login.LoginRepository
import vic.sample.chatuisample.mvvm.model.login.LoginResponse
import vic.sample.chatuisample.mvvm.viewmodel.BasicViewModel
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginDataCheck
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginResult
import vic.sample.chatuisample.utility.Tools

class LoginViewModel(
    coroutineScopeProvider: CoroutineScope? = null,
    private val loginRepository: LoginRepository,
    private val tools: Tools
) : BasicViewModel() {

    private val coroutineScope = getViewModelScope(coroutineScopeProvider)

    private val isLoading = MutableLiveData<Boolean>()
    private val loginResult = MutableLiveData<LoginResult>()
    private val loginInputDataCheck = MutableLiveData<LoginDataCheck>()
    private val isLogin = MutableLiveData<Boolean>()

    fun getIsLoadingStatus(): LiveData<Boolean> {
        return isLoading
    }

    fun getLoginResult(): LiveData<LoginResult> {
        return loginResult
    }

    fun getLoginCheckInputData(): LiveData<LoginDataCheck> {
        return loginInputDataCheck
    }


    fun getIsLoginStatus(): LiveData<Boolean> {
        return isLogin
    }

    fun onCheckIsLogin() {
        isLogin.value = loginRepository.isLoggedIn
    }

    fun onLogin(account: String, password: String) {
        /*
        * To simulate the login status
        * */
        coroutineScope.launch(Main) {

            isLoading.value = true

            val result = loginRepository.login(account, password)

            if (result.status == AboutLoginOrOutStatus.SUCCESS) {
                loginResult.value = LoginResult(success = result.userObj)
            } else {
                loginResult.value = LoginResult(error = R.string.login_failed)
            }

            isLoading.value = false
        }

    }

    fun onLoginInputChange(account: String, password: String) {
        if (!tools.checkEmail(account)) {
            loginInputDataCheck.value = LoginDataCheck(accountError = R.string.login_invalid_email)
        } else if (!isPasswordValid(password)) {
            loginInputDataCheck.value =
                LoginDataCheck(passwordError = R.string.login_invalid_password)
        } else {
            loginInputDataCheck.value = LoginDataCheck(isDataValid = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}