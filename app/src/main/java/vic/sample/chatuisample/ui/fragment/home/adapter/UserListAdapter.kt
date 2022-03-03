package vic.sample.chatuisample.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vic.sample.chatuisample.R
import vic.sample.chatuisample.databinding.RowUserListBinding
import vic.sample.chatuisample.utility.ViewClick


class UserListAdapter(
    private val dataList: ArrayList<UserItem>,
    private val onClickListener: ((view: View, clickItem: UserItem) -> Unit)
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private lateinit var binding: RowUserListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RowUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClickListener)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, position)

    }

    fun getDataList(): ArrayList<UserItem> {
        return dataList
    }

    class ViewHolder(
        private val binding: RowUserListBinding,
        private val onClickListener: ((view: View, clickItem: UserItem) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserItem, position: Int) {
            binding.userListTxtUserName.text = item.name
            binding.userListTxtUserEmail.text = item.email
            binding.userListLayOutLayout.transitionName =
                "${binding.root.context.getString(R.string.list_transition_to_chat)}$position"

            binding.userListLayOutLayout.setOnClickListener(
                object : ViewClick() {
                    override fun CustomOnClick(view: View) {
                        onClickListener.invoke(view, item)
                    }
                })
        }
    }
}
