package vic.sample.chatuisample.mvvm.viewmodel.login.item

import androidx.annotation.Keep
import vic.sample.chatuisample.mvvm.model.login.LoggedInUser
import java.io.Serializable

@Keep
data class LoginResult(
    val success: LoggedInUser? = null,
    val error: Int? = null
) : Serializable