package vic.sample.chatuisample.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import vic.sample.chatuisample.R
import vic.sample.chatuisample.config.ConfigValue
import vic.sample.chatuisample.mvvm.model.simulate.FakeUserData
import vic.sample.chatuisample.mvvm.viewmodel.home.HomeViewModel
import vic.sample.chatuisample.ui.activity.home.adapter.UserItem
import vic.sample.chatuisample.ui.activity.home.adapter.UserListAdapter
import vic.sample.chatuisample.ui.activity.login.LoginActivity
import vic.sample.chatuisample.ui.basic.BasicActivity
import vic.sample.chatuisample.ui.fragment.UserChatFragment

class HomeActivity : BasicActivity(), HomeCallback {

    private val wImgBtbLogout: ImageView by lazy { home_img_btn_logout }
    private val wTxtUserNameTitle: TextView by lazy { home_txt_user_name }
    private val wTxtUserEmail: TextView by lazy { home_txt_user_email }
    private val wRecycleView: RecyclerView by lazy { home_recycle_view_user_list }

    private val mHomeViewModel: HomeViewModel by inject()

    private val mUserList = ArrayList<UserItem>()
    private val mUserListAdapter: UserListAdapter by lazy {
        UserListAdapter(mContext, mUserList) { view, clickItem ->
            connectUserToChat(clickItem)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewValueSet()
        viewListenerSet()
    }

    private fun viewValueSet() {
        mHomeViewModel.getLogoutResult().observe(
            this@HomeActivity, { logoutResult ->
                logoutResult.success?.let {
                    //Logout Success
                    logout()
                } ?: run {
                    logoutResult.error?.let { errorStrInt ->
                        callToast(getString(errorStrInt), isLong = true)
                    }
                }
            }
        )

        mHomeViewModel.getHomeViewDataResult().observe(
            this@HomeActivity, { homeViewData ->
                wTxtUserNameTitle.text = homeViewData.userData?.displayName
                wTxtUserEmail.text = homeViewData.userData?.email
            }
        )

        mHomeViewModel.initHomeView()

        initFakeUserList()
    }

    private fun viewListenerSet() {
        wImgBtbLogout.setOnClickListener {
            mHomeViewModel.onLogout()
        }
    }

    private fun initFakeUserList() {
        mUserList.clear()

        for (i in FakeUserData.getUserName().indices) {
            val userName = FakeUserData.getUserName()[i]
            val userEmail = FakeUserData.getUserEmail()[i]
            mUserList.add(UserItem(userName, userEmail))
        }

        wRecycleView.apply {
            adapter = mUserListAdapter
            layoutManager = LinearLayoutManager(
                this@HomeActivity, RecyclerView.VERTICAL, false
            )
            itemAnimator = DefaultItemAnimator()
        }
    }

    /*
    * About Fragment
    * */
    private fun getFragmentView(fragmentTag: String, o: Any?) {
        val myFragment: Fragment? = when (fragmentTag) {
            ConfigValue.USER_CHAT_FRG_TAG -> {
                UserChatFragment.newInstance(o as UserItem)
            }
            else -> null
        }

        myFragment?.let {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.animator.fragment_anim_slide_up,
                    R.animator.fragment_anim_slide_do_nothing,
                    R.animator.fragment_anim_slide_do_nothing,
                    R.animator.fragment_anim_slide_down
                )
                .add(R.id.home_lay_frg_container, it, fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss()
        }
    }

    /*
    * Callback fun
    * */
    override fun logout() {
        startActivity(
            Intent(this@HomeActivity, LoginActivity::class.java)
        )
        this@HomeActivity.finish()
    }

    override fun connectUserToChat(user: UserItem) {
        getFragmentView(ConfigValue.USER_CHAT_FRG_TAG, user)
    }

}