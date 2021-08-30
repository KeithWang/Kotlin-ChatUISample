package vic.sample.chatuisample.mvvm.viewmodel.home.item

import androidx.annotation.Keep
import vic.sample.chatuisample.mvvm.model.login.LoggedInUser
import java.io.Serializable

@Keep
data class HomeViewData(
        val isLogin: Boolean = false,
        val userData: LoggedInUser? = null
) : Serializable