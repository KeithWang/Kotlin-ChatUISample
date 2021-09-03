package vic.sample.chatuisample.ui.fragment.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_user_list.view.*
import kotlinx.android.synthetic.main.row_user_list.view.user_list_txt_user_name
import vic.sample.chatuisample.R
import vic.sample.chatuisample.utility.ViewClick


class UserListAdapter(
    mContext: Context,
    private val items: ArrayList<UserItem>,
    private val onClickListener: ((view: View, clickItem: UserItem) -> Unit)
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.row_user_list, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)

    }

    class ViewHolder(
        private val view: View,
        private val onClickListener: ((view: View, clickItem: UserItem) -> Unit)
    ) : RecyclerView.ViewHolder(view) {
        fun bind(item: UserItem, position: Int) {
            view.user_list_txt_user_name.text = item.name
            view.user_list_txt_user_email.text = item.email
            view.user_list_lay_out_layout.transitionName =
                "${view.context.getString(R.string.list_transition_to_chat)}$position"

            view.user_list_lay_out_layout.setOnClickListener(
                object : ViewClick() {
                    override fun CustomOnClick(view: View) {
                        onClickListener.invoke(view, item)
                    }
                })
        }
    }
}
