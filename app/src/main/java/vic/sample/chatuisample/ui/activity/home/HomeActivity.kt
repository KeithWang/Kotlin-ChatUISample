package vic.sample.chatuisample.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_home.*
import vic.sample.chatuisample.R
import vic.sample.chatuisample.config.ConfigValue
import vic.sample.chatuisample.databinding.ActivityHomeBinding
import vic.sample.chatuisample.ui.fragment.home.adapter.UserItem
import vic.sample.chatuisample.ui.activity.login.LoginActivity
import vic.sample.chatuisample.ui.basic.BasicActivity
import vic.sample.chatuisample.ui.fragment.home.HomeListFragment
import vic.sample.chatuisample.ui.fragment.chat.UserChatFragment

class HomeActivity : BasicActivity(), HomeCallback {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showLoading(show: Boolean) {
        binding.homeLayLoadingArea.visibility = if (show) View.VISIBLE else View.GONE
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
}