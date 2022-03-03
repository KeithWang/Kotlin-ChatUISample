package vic.sample.chatuisample.mvvm.viewmodel.login.item

import vic.sample.chatuisample.mvvm.model.login.LoggedInUser

sealed class LoginStatus {
    data class OnApiLoading(val isLoading: Boolean) : LoginStatus()
    data class OnApiSuccess(val userInfo: LoggedInUser) : LoginStatus()
    data class OnApiFail(val errorStrInt: Int) : LoginStatus()
}
