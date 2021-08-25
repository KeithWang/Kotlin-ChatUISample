package vic.sample.chatuisample.mvvm.viewmodel.login

interface LoginCallback {
    fun onShowLoading(show: Boolean)

    fun openHomePage()
}