package vic.sample.chatuisample.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_chat.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vic.sample.chatuisample.R
import vic.sample.chatuisample.mvvm.model.simulate.FakeUserData
import vic.sample.chatuisample.ui.activity.home.HomeCallback
import vic.sample.chatuisample.ui.activity.home.adapter.UserItem
import vic.sample.chatuisample.ui.basic.BasicFragment
import vic.sample.chatuisample.ui.fragment.adapter.ChatAdapter
import vic.sample.chatuisample.ui.fragment.adapter.ChatItem
import vic.sample.chatuisample.ui.fragment.adapter.ChatViewType
import java.util.*

class UserChatFragment : BasicFragment() {

    private val wImgBtbClose: ImageView by lazy { user_chat_img_btn_close }
    private val wTxtUserNameTitle: TextView by lazy { user_chat_txt_user_name }
    private val wTxtUserEmail: TextView by lazy { user_chat_txt_user_email }
    private val wLayBtnSendMsg: FrameLayout by lazy { user_chat_lay_btn_send }
    private val wEditMsgInput: EditText by lazy { user_chat_edit_input_msg }

    private lateinit var mMainCallback: HomeCallback

    private var mUserData: UserItem? = null

    /*
    * Chat List
    * */
    private val wRecycleViewChat: RecyclerView by lazy { user_chat_recycle_view_chat_list }
    private val mChatItemList = ArrayList<ChatItem>()
    private val mChatAdapter: ChatAdapter by lazy {
        ChatAdapter(mContext, mChatItemList)
    }


    companion object {
        private const val USER_CHART_OBJ_KET = "USER_CHART_OBJ_KET"

        fun newInstance(user: UserItem) = UserChatFragment().apply {
            arguments = bundleOf(USER_CHART_OBJ_KET to user)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onHideKeyboard()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mMainCallback = context as HomeCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$context must implement HomeCallback"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_user_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewValueSet()
        viewListenerSet()
    }


    private fun viewValueSet() {
        arguments?.let {
            mUserData = it.get(USER_CHART_OBJ_KET) as UserItem
        }

        mUserData?.let {
            wTxtUserNameTitle.text = it.name
            wTxtUserEmail.text = it.email
        }

        initChatRecycleView()
    }

    private fun viewListenerSet() {
        wImgBtbClose.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }

        wLayBtnSendMsg.setOnClickListener {
            val msg = wEditMsgInput.text.toString()
            if (msg.isNotEmpty()) {
                addNesMsg(msg, System.currentTimeMillis(), ChatViewType.VIEW_TYPE_SEND)
                wEditMsgInput.setText("")

                val fakeResponse = FakeUserData.getUserResponse()

                GlobalScope.launch(Main) {
                    delay((0..2000).random().toLong())
                    addNesMsg(
                        fakeResponse[(fakeResponse.indices).random()], System.currentTimeMillis()
                    )
                }

            }
        }
    }

    private fun initChatRecycleView() {
        wRecycleViewChat.apply {
            adapter = mChatAdapter
            layoutManager = LinearLayoutManager(
                mContext, RecyclerView.VERTICAL, false
            )

            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun addNesMsg(
        contain: String, msgUtcTime: Long, viewType: ChatViewType = ChatViewType.VIEW_TYPE_RECEIVE,
    ) {
        mChatItemList.add(ChatItem(contain, msgUtcTime, viewType))
        mChatAdapter.notifyItemChanged(mChatItemList.size - 1)
        wRecycleViewChat.post {
            wRecycleViewChat.smoothScrollToPosition(mChatItemList.size - 1)
        }

    }
}
