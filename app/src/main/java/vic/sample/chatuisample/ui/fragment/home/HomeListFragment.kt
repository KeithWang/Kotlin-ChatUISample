package vic.sample.chatuisample.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.doOnPreDraw
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.Hold
import org.koin.android.ext.android.inject
import vic.sample.chatuisample.R
import vic.sample.chatuisample.databinding.FragmentHomeListBinding
import vic.sample.chatuisample.mvvm.model.simulate.FakeUserData
import vic.sample.chatuisample.mvvm.viewmodel.home.HomeViewModel
import vic.sample.chatuisample.ui.activity.home.HomeCallback
import vic.sample.chatuisample.ui.fragment.home.adapter.UserItem
import vic.sample.chatuisample.ui.fragment.home.adapter.UserListAdapter
import vic.sample.chatuisample.ui.basic.BasicFragment
import java.util.*

class HomeListFragment : BasicFragment() {

    private lateinit var mMainCallback: HomeCallback

    private lateinit var binding: FragmentHomeListBinding

    private val mHomeViewModel: HomeViewModel by inject()

    private val mUserList = ArrayList<UserItem>()

    private val mAdapter : UserListAdapter by lazy {
        UserListAdapter(mUserList) { view, clickItem ->
            val extras =
                FragmentNavigatorExtras(view to getString(R.string.list_transition_to_chat))
            val direction: NavDirections =
                HomeListFragmentDirections.actionHomeListFragmentToUserChatFragment(clickItem)
            findNavController().navigate(direction, extras)
        }
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
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        viewValueSet()
        viewListenerSet()
    }


    private fun viewValueSet() {
        mHomeViewModel.getIsLoadingStatus().observe(
            viewLifecycleOwner, {
                mMainCallback.showLoading(it)
            }
        )

        mHomeViewModel.getLogoutResult().observe(
            viewLifecycleOwner, { logoutResult ->
                logoutResult.success?.let {
                    //Logout Success
                    mMainCallback.logout()
                } ?: run {
                    logoutResult.error?.let { errorStrInt ->
                        callToast(getString(errorStrInt), isLong = true)
                    }
                }
            }
        )

        mHomeViewModel.getHomeViewDataResult().observe(
            viewLifecycleOwner, { homeViewData ->
                if (homeViewData.isLogin) {
                    binding.homeListTxtUserName.text = homeViewData.userData?.displayName
                    binding.homeListTxtUserEmail.text = homeViewData.userData?.email
                } else {
                    mHomeViewModel.onLogout()
                }
            }
        )

        mHomeViewModel.onInitHomeView()

        initFakeUserList()

    }

    private fun viewListenerSet() {
        binding.homeListImgBtnLogout.setOnClickListener {
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

        binding.homeListRecycleViewUserList.apply {
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

}
