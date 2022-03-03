package vic.sample.chatuisample.mvvm.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import vic.sample.chatuisample.R
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import vic.sample.chatuisample.mvvm.model.login.LoginRepository
import vic.sample.chatuisample.mvvm.viewmodel.BasicViewModel
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginDataCheck
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginStatus
import vic.sample.chatuisample.utility.Tools

class LoginViewModel(
    coroutineScopeProvider: CoroutineScope? = null,
    private val loginRepository: LoginRepository,
    private val tools: Tools
) : BasicViewModel() {

    private val coroutineScope = getViewModelScope(coroutineScopeProvider)

    private val loginStatus = MutableLiveData<LoginStatus>()
    private val loginInputDataCheck = MutableLiveData<LoginDataCheck>()
    private val isLogin = MutableLiveData<Boolean>()

    fun getLoginResult(): LiveData<LoginStatus> {
        return loginStatus
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

            loginStatus.value = LoginStatus.OnApiLoading(true)

            val result = loginRepository.login(account, password)

            if (result.status == AboutLoginOrOutStatus.SUCCESS) {
                result.userObj?.let { userInfo ->
                    loginStatus.value = LoginStatus.OnApiSuccess(userInfo = userInfo)
                } ?: run {
                    loginStatus.value = LoginStatus.OnApiFail(errorStrInt = R.string.login_user_fail)
                }
            } else {
                loginStatus.value = LoginStatus.OnApiFail(errorStrInt = R.string.login_failed)
            }

            loginStatus.value = LoginStatus.OnApiLoading(false)

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