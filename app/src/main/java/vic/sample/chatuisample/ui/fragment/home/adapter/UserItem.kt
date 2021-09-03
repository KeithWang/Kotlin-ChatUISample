package vic.sample.chatuisample.ui.fragment.home.adapter

import androidx.annotation.Keep
import java.io.Serializable


@Keep
data class UserItem(
    val name: String = "", val email: String = ""
) : Serializable
