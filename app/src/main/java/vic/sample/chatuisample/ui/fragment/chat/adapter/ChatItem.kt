package vic.sample.chatuisample.ui.fragment.chat.adapter

import androidx.annotation.Keep
import java.io.Serializable


@Keep
data class ChatItem(
    var contain: String? = "",
    var msgUtc: Long = 0L,
    val viewType: ChatViewType? = ChatViewType.VIEW_TYPE_RECEIVE,
) : Serializable
