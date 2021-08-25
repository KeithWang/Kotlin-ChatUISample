package vic.sample.chatuisample.mvvm.viewmodel.home.item

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class LogoutResult(
        val success: Boolean? = false,
        val error: Int? = null
) : Serializable