package vic.sample.chatuisample.ui.fragment.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_user_chat_receive_message.view.*
import kotlinx.android.synthetic.main.row_user_chat_send_message.view.*
import vic.sample.chatuisample.R
import java.text.SimpleDateFormat
import java.util.*


class ChatAdapter(
    context: Context, private val items: List<ChatItem>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    private val dateFormatter = SimpleDateFormat("MMM d, HH:mm:ss", Locale.getDefault())

    override fun getItemViewType(position: Int): Int {
        if (items[position].viewType == ChatViewType.VIEW_TYPE_RECEIVE) {
            return ChatViewType.VIEW_TYPE_RECEIVE.value
        }
        return ChatViewType.VIEW_TYPE_SEND.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ChatViewType.VIEW_TYPE_RECEIVE.value) {
            val view = mInflater.inflate(
                R.layout.row_user_chat_receive_message, parent, false
            )
            ViewHolderReceive(view, dateFormatter)
        } else {
            val view = mInflater.inflate(
                R.layout.row_user_chat_send_message, parent, false
            )
            ViewHolderSend(view, dateFormatter)
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
        private val view: View, private val dateFormatter: SimpleDateFormat
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: ChatItem) {
            view.msg_receive_txt_msg.text = item.contain
            view.msg_receive_txt_time.text = dateFormatter.format(Date(item.msgUtc))
            view.msg_receive_txt_msg.requestLayout()
        }
    }

    class ViewHolderSend(
        private val view: View, private val dateFormatter: SimpleDateFormat
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: ChatItem) {
            view.msg_send_txt_msg.text = item.contain
            view.msg_send_txt_time.text = dateFormatter.format(Date(item.msgUtc))
            view.msg_send_txt_msg.requestLayout()
        }
    }
}
