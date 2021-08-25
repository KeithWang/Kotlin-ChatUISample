package vic.sample.chatuisample.mvvm.viewmodel.login.item

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class LoginDataCheck(
        val accountError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false,
) : Serializable