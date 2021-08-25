package vic.sample.chatuisample.mvvm.viewmodel.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import vic.sample.chatuisample.R
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import vic.sample.chatuisample.mvvm.model.login.LoginRepository
import vic.sample.chatuisample.mvvm.viewmodel.BasicViewModel
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginDataCheck
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginResult

class LoginViewModel(private val loginRepository: LoginRepository) : BasicViewModel() {

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

    fun login(account: String, password: String) {
        val result = loginRepository.login(account, password)

        if (result.status == AboutLoginOrOutStatus.SUCCESS) {
            /*
            * To simulate the login status
            * */
            viewModelScope.launch(Main) {
                isLoading.value = true
                withContext(IO) {
                    delay(2000)
                }
                isLoading.value = false
                loginResult.value = LoginResult(success = result.userObj)
            }

        } else {
            loginResult.value = LoginResult(error = R.string.login_failed)
        }

    }

    fun onLoginInputChange(account: String, password: String) {
        if (!isAccountValid(account)) {
            loginInputDataCheck.value = LoginDataCheck(accountError = R.string.login_invalid_email)
        } else if (!isPasswordValid(password)) {
            loginInputDataCheck.value =
                LoginDataCheck(passwordError = R.string.login_invalid_password)
        } else {
            loginInputDataCheck.value = LoginDataCheck(isDataValid = true)
        }
    }

    private fun isAccountValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}