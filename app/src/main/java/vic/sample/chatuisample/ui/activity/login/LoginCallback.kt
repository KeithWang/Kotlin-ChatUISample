package vic.sample.chatuisample.ui.activity.login

interface LoginCallback {
    fun onShowLoading(show: Boolean)

    fun openHomePage()
}