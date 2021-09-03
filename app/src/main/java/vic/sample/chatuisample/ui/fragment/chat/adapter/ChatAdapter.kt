package vic.sample.chatuisample.ui.fragment.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vic.sample.chatuisample.databinding.RowUserChatReceiveMessageBinding
import vic.sample.chatuisample.databinding.RowUserChatSendMessageBinding
import java.text.SimpleDateFormat
import java.util.*


class ChatAdapter(
    private val items: List<ChatItem>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dateFormatter = SimpleDateFormat("MMM d, HH:mm:ss", Locale.getDefault())

    private lateinit var sendBinding: RowUserChatSendMessageBinding
    private lateinit var receiveBinding: RowUserChatReceiveMessageBinding

    override fun getItemViewType(position: Int): Int {
        if (items[position].viewType == ChatViewType.VIEW_TYPE_RECEIVE) {
            return ChatViewType.VIEW_TYPE_RECEIVE.value
        }
        return ChatViewType.VIEW_TYPE_SEND.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ChatViewType.VIEW_TYPE_RECEIVE.value) {
            receiveBinding = RowUserChatReceiveMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

            ViewHolderReceive(receiveBinding, dateFormatter)
        } else {
            sendBinding = RowUserChatSendMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ViewHolderSend(sendBinding, dateFormatter)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (item.viewType == ChatViewType.VIEW_TYPE_RECEIVE) {
            (holder as ViewHolderReceive).bind(item)
        } else if (item.viewType == ChatViewType.VIEW_TYPE_SEND) {
            (holder as ViewHolderSend).bind(item)
        }
    }

    class ViewHolderReceive(
        private val receiveBinding: RowUserChatReceiveMessageBinding,
        private val dateFormatter: SimpleDateFormat
    ) : RecyclerView.ViewHolder(receiveBinding.root) {

        fun bind(item: ChatItem) {
            receiveBinding.msgReceiveTxtMsg.text = item.contain
            receiveBinding.msgReceiveTxtTime.text = dateFormatter.format(Date(item.msgUtc))
            receiveBinding.msgReceiveTxtMsg.requestLayout()
        }
    }

    class ViewHolderSend(
        private val sendBinding: RowUserChatSendMessageBinding,
        private val dateFormatter: SimpleDateFormat
    ) : RecyclerView.ViewHolder(sendBinding.root) {

        fun bind(item: ChatItem) {
            sendBinding.msgSendTxtMsg.text = item.contain
            sendBinding.msgSendTxtTime.text = dateFormatter.format(Date(item.msgUtc))
            sendBinding.msgSendTxtMsg.requestLayout()
        }
    }
}
