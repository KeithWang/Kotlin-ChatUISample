package vic.sample.chatuisample.mvvm.model.login.logout

import androidx.annotation.Keep
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import java.io.Serializable

@Keep
data class LogoutResponse(
    val status: AboutLoginOrOutStatus, val container: String = ""
) : Serializable