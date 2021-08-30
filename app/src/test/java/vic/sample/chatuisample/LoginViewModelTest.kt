package vic.sample.chatuisample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import vic.sample.chatuisample.mvvm.model.login.AboutLoginOrOutStatus
import vic.sample.chatuisample.mvvm.model.login.LoggedInUser
import vic.sample.chatuisample.mvvm.model.login.LoginRepository
import vic.sample.chatuisample.mvvm.model.login.LoginResponse
import vic.sample.chatuisample.mvvm.viewmodel.login.LoginViewModel
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginDataCheck
import vic.sample.chatuisample.mvvm.viewmodel.login.item.LoginResult
import vic.sample.chatuisample.utility.Tools

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginRope: LoginRepository

    @Mock
    private lateinit var mTools: Tools

    @Mock
    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var loginInputCheckObserve: Observer<LoginDataCheck>

    @Mock
    private lateinit var loginStatusObserve: Observer<LoginResult>

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @ExperimentalCoroutinesApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = LoginViewModel(
            coroutineScopeProvider = testScope, loginRepository = loginRope, tools = mTools
        )

        viewModel.getLoginCheckInputData().observeForever(loginInputCheckObserve)
        viewModel.getLoginResult().observeForever(loginStatusObserve)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `When Login Input Change - The email check format error`() {
        val emailErrorExpect = LoginDataCheck(accountError = R.string.login_invalid_email)

        val email = "testEmailError"
        val password = "123456"

        /*
        * To simulate the Email Error
        * */
        `when`(mTools.checkEmail(email)).thenReturn(false)

        viewModel.onLoginInputChange(account = email, password = password)
        verify(loginInputCheckObserve).onChanged(emailErrorExpect)
    }

    @Test
    fun `When Login Input Change - The email check format success but password length error`() {
        val passwordErrorExpect = LoginDataCheck(passwordError = R.string.login_invalid_password)

        val email = "test@gmail.com"
        val password = "123"

        /*
        * To simulate the Email Success
        * */
        `when`(mTools.checkEmail(email)).thenReturn(true)

        viewModel.onLoginInputChange(account = email, password = password)
        verify(loginInputCheckObserve).onChanged(passwordErrorExpect)
    }

    @Test
    fun `When Login Input Change - The email and password pass`() {
        val successExpect = LoginDataCheck(isDataValid = true)

        val email = "test@gmail.com"
        val password = "123456"

        /*
        * To simulate the Email Success
        * */
        `when`(mTools.checkEmail(email)).thenReturn(true)

        viewModel.onLoginInputChange(account = email, password = password)
        verify(loginInputCheckObserve).onChanged(successExpect)
    }

    @Test
    fun `Login - Login failed`() = runBlockingTest {
        val failedExpect = LoginResult(error = R.string.login_failed)

        val email = "test@gmail.com"
        val password = "123456"
        /*
        * To simulate the login (For Example : from API)
        * */
        val loginStatus = LoginResponse(AboutLoginOrOutStatus.FAILED)
        `when`(loginRope.login(username = email, password = password)).thenReturn(loginStatus)

        viewModel.onLogin(account = email, password = password)

        verify(loginStatusObserve).onChanged(failedExpect)
    }

    @Test
    fun `Login - Login success`() = runBlockingTest {
        val email = "test@gmail.com"
        val password = "123456"
        val id = "testForId"
        val name = "Vic"

        val userObj = LoggedInUser(userId = id, displayName = name, email = email)
        val successExpect = LoginResult(success = userObj)
        /*
        * To simulate the login (For Example : from API)
        * */
        val loginStatus = LoginResponse(AboutLoginOrOutStatus.SUCCESS, userObj = userObj)
        `when`(loginRope.login(username = email, password = password)).thenReturn(loginStatus)

        viewModel.onLogin(account = email, password = password)

        verify(loginStatusObserve).onChanged(successExpect)
    }
}
