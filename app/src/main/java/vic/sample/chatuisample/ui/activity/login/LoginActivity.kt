package vic.sample.chatuisample.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import vic.sample.chatuisample.R
import vic.sample.chatuisample.databinding.ActivityLoginBinding
import vic.sample.chatuisample.mvvm.viewmodel.login.LoginViewModel
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginStatus
import vic.sample.chatuisample.ui.activity.home.HomeActivity
import vic.sample.chatuisample.ui.basic.BasicActivity
import vic.sample.chatuisample.utility.ViewClick

class LoginActivity : BasicActivity(), LoginCallback {

    private lateinit var binding: ActivityLoginBinding

    private val mLoginViewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewValueSet()
        viewListenerSet()
    }

    private fun viewValueSet() {

        mLoginViewModel.getIsLoginStatus().observe(
            this@LoginActivity, {
                if (it) {
                    openHomePage()
                }
            }
        )

        mLoginViewModel.getLoginResult().observe(
            this@LoginActivity, {
                when (it) {
                    is LoginStatus.OnApiLoading -> {
                        onShowLoading(it.isLoading)
                    }
                    is LoginStatus.OnApiFail -> {
                        callToast(getString(it.errorStrInt), true)
                    }
                    is LoginStatus.OnApiSuccess -> {
                        //Jump Other Page
                        val welcomeStr =
                            "${getString(R.string.login_welcome)} ${it.userInfo.displayName}!"
                        callToast(welcomeStr, true)

                        openHomePage()
                    }
                }
            }
        )

        mLoginViewModel.getLoginCheckInputData().observe(
            this@LoginActivity, { loginCheckObj ->

                binding.loginBtnSignIn.isEnabled = loginCheckObj.isDataValid

                loginCheckObj.accountError?.let { accountErrorIntSource ->
                    binding.loginEditAccountEmail.error = getString(accountErrorIntSource)
                }

                loginCheckObj.passwordError?.let { pwdErrorIntSource ->
                    binding.loginEditPassword.error = getString(pwdErrorIntSource)
                }
            }
        )

        mLoginViewModel.onCheckIsLogin()
    }

    private fun viewListenerSet() {
        binding.loginBtnSignIn.setOnClickListener(mNormalClickListener)
        binding.loginTxtBtnForgetPwd.setOnClickListener(mNormalClickListener)
        binding.loginTxtBtnCreateNewAccount.setOnClickListener(mNormalClickListener)

        binding.loginEditAccountEmail.afterTextChanged {
            mLoginViewModel.onLoginInputChange(
                binding.loginEditAccountEmail.text.toString(),
                binding.loginEditPassword.text.toString()
            )
        }

        binding.loginEditPassword.afterTextChanged {
            mLoginViewModel.onLoginInputChange(
                binding.loginEditAccountEmail.text.toString(),
                binding.loginEditPassword.text.toString()
            )
        }
    }

    private val mNormalClickListener = object : ViewClick() {
        override fun CustomOnClick(view: View) {
            when (view.id) {
                R.id.login_btn_sign_in -> {
                    mLoginViewModel.onLogin(
                        binding.loginEditAccountEmail.text.toString(),
                        binding.loginEditPassword.text.toString()
                    )
                    onHideKeyboard()
                    binding.loginEditAccountEmail.clearFocus()
                    binding.loginEditPassword.clearFocus()
                }
                R.id.login_txt_btn_forget_pwd -> {
                    callToast(getString(R.string.login_forget_pwd), isLong = true)
                }
                R.id.login_txt_btn_create_new_account -> {
                    callToast(getString(R.string.login_create_new_account), isLong = true)
                }
            }
        }

    }

    /*
    * Callback fun
    * */
    override fun onShowLoading(show: Boolean) {
        binding.loginLayLoadingArea.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun openHomePage() {
        startActivity(
            Intent(this@LoginActivity, HomeActivity::class.java)
        )
        this@LoginActivity.finish()
    }
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}