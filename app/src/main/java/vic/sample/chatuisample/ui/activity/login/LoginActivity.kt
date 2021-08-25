package vic.sample.chatuisample.ui.activity.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import vic.sample.chatuisample.R
import vic.sample.chatuisample.mvvm.viewmodel.login.LoginCallback
import vic.sample.chatuisample.mvvm.viewmodel.login.LoginViewModel
import vic.sample.chatuisample.ui.activity.home.HomeActivity
import vic.sample.chatuisample.ui.basic.BasicActivity
import vic.sample.chatuisample.utility.ViewClick

class LoginActivity : BasicActivity(), LoginCallback {

    private val wLayLoadingArea: FrameLayout by lazy { login_lay_loading_area }
    private val wEditEmail: EditText by lazy { login_edit_account_email }
    private val wEditPwd: EditText by lazy { login_edit_password }
    private val wBtnSignIn: Button by lazy { login_btn_sign_in }
    private val wTxtBtnForgetPwd: TextView by lazy { login_txt_btn_forget_pwd }
    private val wTxtBtnCreateNewAccount: TextView by lazy { login_txt_btn_create_new_account }

    private val mLoginViewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewValueSet()
        viewListenerSet()
    }

    private fun viewValueSet() {

        mLoginViewModel.getIsLoadingStatus().observe(
            this@LoginActivity, {
                onShowLoading(it)
            }
        )

        mLoginViewModel.getIsLoginStatus().observe(
            this@LoginActivity, {
                if (it) {
                    openHomePage()
                }
            }
        )

        mLoginViewModel.getLoginResult().observe(
            this@LoginActivity, {
                it.error?.let { errorStringInt ->
                    callToast(getString(errorStringInt), true)
                } ?: run {
                    it.success?.let { userObj ->
                        //Jump Other Page
                        val welcomeStr =
                            "${getString(R.string.login_welcome)} ${userObj.displayName}!"
                        callToast(welcomeStr, true)

                        openHomePage()
                    }
                }
            }
        )

        mLoginViewModel.getLoginCheckInputData().observe(
            this@LoginActivity, { loginCheckObj ->

                wBtnSignIn.isEnabled = loginCheckObj.isDataValid

                loginCheckObj.accountError?.let { accountErrorIntSource ->
                    wEditEmail.error = getString(accountErrorIntSource)
                }

                loginCheckObj.passwordError?.let { pwdErrorIntSource ->
                    wEditPwd.error = getString(pwdErrorIntSource)
                }
            }
        )

        mLoginViewModel.onCheckIsLogin()
    }

    private fun viewListenerSet() {
        wBtnSignIn.setOnClickListener(mNormalClickListener)
        wTxtBtnForgetPwd.setOnClickListener(mNormalClickListener)
        wTxtBtnCreateNewAccount.setOnClickListener(mNormalClickListener)

        wEditEmail.afterTextChanged {
            mLoginViewModel.onLoginInputChange(
                wEditEmail.text.toString(),
                wEditPwd.text.toString()
            )
        }

        wEditPwd.afterTextChanged {
            mLoginViewModel.onLoginInputChange(
                wEditEmail.text.toString(),
                wEditPwd.text.toString()
            )
        }
    }

    private val mNormalClickListener = object : ViewClick() {
        override fun CustomOnClick(view: View) {
            when (view.id) {
                R.id.login_btn_sign_in -> {
                    mLoginViewModel.login(wEditEmail.text.toString(), wEditPwd.text.toString())
                    onHideKeyboard()
                    wEditEmail.clearFocus()
                    wEditPwd.clearFocus()
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
        wLayLoadingArea.visibility = if (show) View.VISIBLE else View.GONE
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