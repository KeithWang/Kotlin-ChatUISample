package vic.sample.chatuisample.mvvm.model.login

import vic.sample.chatuisample.mvvm.model.login.logout.LogoutResponse
import vic.sample.chatuisample.mvvm.model.simulate.LoginOrLogoutDataSource

class LoginRepository(val orLogoutDataSource: LoginOrLogoutDataSource) {


    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        /*
        * You can use your familiar tools to keep the data.
        * */
        val userCache = orLogoutDataSource.loadUserData()

        user = if (userCache.userId != "" && userCache.displayName != "" && userCache.email != "")
            userCache
        else
            null
    }

    fun logout(): LogoutResponse {
        val logoutResult = orLogoutDataSource.logout(user)

        if (logoutResult.status == AboutLoginOrOutStatus.SUCCESS) {
            user = null
        }

        return logoutResult
    }

    fun login(username: String, password: String): LoginResponse {
        val loginResponse = orLogoutDataSource.login(username, password)

        loginResponse.userObj?.let {
            setLoggedInUser(loginResponse.userObj)
        }

        return loginResponse
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}