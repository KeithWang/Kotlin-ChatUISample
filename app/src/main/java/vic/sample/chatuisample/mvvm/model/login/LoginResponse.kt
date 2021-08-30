package vic.sample.chatuisample.mvvm.model.login

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class LoginResponse(
    val status: AboutLoginOrOutStatus = AboutLoginOrOutStatus.FAILED, val container: String = "", val userObj: LoggedInUser? = null
) : Serializable