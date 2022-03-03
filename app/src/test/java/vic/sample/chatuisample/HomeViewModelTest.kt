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
import vic.sample.chatuisample.mvvm.model.login.logout.LogoutResponse
import vic.sample.chatuisample.mvvm.viewmodel.home.HomeViewModel
import vic.sample.chatuisample.mvvm.viewmodel.home.item.HomeViewData
import vic.sample.chatuisample.mvvm.viewmodel.home.item.LogoutResult

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginRope: LoginRepository

    @Mock
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var homeViewDataObserve: Observer<HomeViewData>

    @Mock
    private lateinit var logoutStatusObserve: Observer<LogoutResult>

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @ExperimentalCoroutinesApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = HomeViewModel(coroutineScopeProvider = testScope, loginRepository = loginRope)

        viewModel.getHomeViewDataResult().observeForever(homeViewDataObserve)
        viewModel.getLogoutResult().observeForever(logoutStatusObserve)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `To init the home view - The User Error`() {
        val userErrorExpect = HomeViewData(userData = null)

        `when`(loginRope.isLoggedIn).thenReturn(false)
        `when`(loginRope.user).thenReturn(null)
        viewModel.onInitHomeView()

        verify(homeViewDataObserve).onChanged(userErrorExpect)
    }

    @Test
    fun `To init the home view - The User Exist`() {
        val email = "test@gmail.com"
        val id = "testForId"
        val name = "Vic"
        val userObj = LoggedInUser(userId = id, displayName = name, email = email)

        val userExistExpect = HomeViewData(isLogin = true, userData = userObj)

        `when`(loginRope.isLoggedIn).thenReturn(true)
        `when`(loginRope.user).thenReturn(userObj)
        viewModel.onInitHomeView()

        verify(homeViewDataObserve).onChanged(userExistExpect)
    }

    @Test
    fun `Logout - Logout failed`() = runBlockingTest {
        val failedExpect = LogoutResult(error = R.string.home_logout_error)
        /*
        * To simulate the logout (For Example : from API)
        * */
        val logoutStatus = LogoutResponse(AboutLoginOrOutStatus.FAILED)
        `when`(loginRope.logout()).thenReturn(logoutStatus)

        viewModel.onLogout()

        verify(logoutStatusObserve).onChanged(failedExpect)
    }

    @Test
    fun `Logout - Logout success`() = runBlockingTest {
        val successExpect = LogoutResult(success = true)
        /*
        * To simulate the logout (For Example : from API)
        * */
        val logoutStatus = LogoutResponse(AboutLoginOrOutStatus.SUCCESS)
        `when`(loginRope.logout()).thenReturn(logoutStatus)

        viewModel.onLogout()

        verify(logoutStatusObserve).onChanged(successExpect)
    }

}
