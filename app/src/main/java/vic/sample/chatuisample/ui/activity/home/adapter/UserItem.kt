package vic.sample.chatuisample.ui.activity.home.adapter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Keep
data class UserItem(
    val name: String = "", val email: String = ""
) : Serializable
