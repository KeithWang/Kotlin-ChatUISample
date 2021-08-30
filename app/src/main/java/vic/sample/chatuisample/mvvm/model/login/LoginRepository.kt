package vic.sample.chatuisample.mvvm.model.login

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
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

    suspend fun logout(): LogoutResponse = withContext(IO) {
        /*
        * To simulate the loading progress
        * */
        delay(2000)
        val logoutResult = orLogoutDataSource.logout(user)

        if (logoutResult.status == AboutLoginOrOutStatus.SUCCESS) {
            user = null
        }

        return@withContext logoutResult
    }

    suspend fun login(username: String, password: String): LoginResponse = withContext(IO) {

        /*
        * To simulate the loading progress
        * */
        delay(2000)
        val loginResponse = orLogoutDataSource.login(username, password)

        loginResponse.userObj?.let {
            setLoggedInUser(loginResponse.userObj)
        }

        return@withContext loginResponse
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}