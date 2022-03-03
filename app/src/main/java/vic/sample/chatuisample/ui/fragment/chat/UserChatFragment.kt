package vic.sample.chatuisample.ui.fragment.chat

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vic.sample.chatuisample.R
import vic.sample.chatuisample.databinding.FragmentUserChatBinding
import vic.sample.chatuisample.mvvm.model.simulate.FakeUserData
import vic.sample.chatuisample.ui.activity.home.HomeCallback
import vic.sample.chatuisample.ui.fragment.home.adapter.UserItem
import vic.sample.chatuisample.ui.basic.BasicFragment
import vic.sample.chatuisample.ui.fragment.chat.adapter.ChatAdapter
import vic.sample.chatuisample.ui.fragment.chat.adapter.ChatItem
import vic.sample.chatuisample.ui.fragment.chat.adapter.ChatViewType
import java.util.*
import kotlin.collections.ArrayList

class UserChatFragment : BasicFragment() {

    private lateinit var mMainCallback: HomeCallback

    private lateinit var binding: FragmentUserChatBinding

    /*
    * To init page data
    * */
    private val args: UserChatFragmentArgs by navArgs()
    private val mUserData: UserItem by lazy { args.userItem }

    /*
    * Chat List
    * */
    private lateinit var mChatAdapter: ChatAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewValueSet()
        viewListenerSet()
    }


    private fun viewValueSet() {

        binding.userChatTxtUserName.text = mUserData.name
        binding.userChatTxtUserEmail.text = mUserData.email

        initChatRecycleView()
    }

    private fun viewListenerSet() {
        binding.userChatImgBtnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.userChatLayBtnSend.setOnClickListener {
            val msg = binding.userChatEditInputMsg.text.toString()
            if (msg.isNotEmpty()) {
                addNesMsg(msg, System.currentTimeMillis(), ChatViewType.VIEW_TYPE_SEND)
                binding.userChatEditInputMsg.setText("")

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
        mChatAdapter = ChatAdapter(ArrayList())

        binding.userChatRecycleViewChatList.apply {
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
        /*
        * You can save the data into the database and use the ViewModel to update the UI.
        * */
        mChatAdapter.addMsg(ChatItem(contain, msgUtcTime, viewType))
        binding.userChatRecycleViewChatList.post {
            binding.userChatRecycleViewChatList.smoothScrollToPosition(mChatAdapter.itemCount - 1)
        }

    }
}
