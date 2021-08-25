package vic.sample.chatuisample.mvvm.model.login

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class LoggedInUser(
        val userId: String, val displayName: String, val email: String,
) : Serializable