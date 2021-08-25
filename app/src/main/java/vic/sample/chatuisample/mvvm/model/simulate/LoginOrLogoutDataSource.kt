package vic.sample.chatuisample.mvvm.model.simulate

import vic.sample.chatuisample.mvvm.model.cache.CacheRepository
import vic.sample.chatuisample.mvvm.model.login.LoggedInUser
import vic.sample.chatuisample.mvvm.model.login.LoginResponse
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import vic.sample.chatuisample.mvvm.model.login.logout.LogoutResponse
import java.net.CacheResponse
import java.util.UUID.randomUUID

class LoginOrLogoutDataSource(val mCache: CacheRepository) {

    fun loadUserData(): LoggedInUser {
        return LoggedInUser(mCache.userID, mCache.userName, mCache.userEmail)
    }

    fun login(emailAccount: String, password: String): LoginResponse {
        return try {
            val userID = randomUUID().toString()
            val userName = "Vic Wang"
            val fakeUser = LoggedInUser(userID, userName, emailAccount)
            mCache.userID = userID
            mCache.userName = userName
            mCache.userEmail = emailAccount

            LoginResponse(AboutLoginOrOutStatus.SUCCESS, userObj = fakeUser)
        } catch (e: Throwable) {
            LoginResponse(AboutLoginOrOutStatus.FAILED, container = e.toString())
        }
    }

    fun logout(userData: LoggedInUser?): LogoutResponse {
        return try {
            mCache.userID = ""
            mCache.userName = ""
            mCache.userEmail = ""
            LogoutResponse(AboutLoginOrOutStatus.SUCCESS)
        } catch (e: Throwable) {
            LogoutResponse(AboutLoginOrOutStatus.FAILED, container = e.toString())
        }
    }
}