package vic.sample.chatuisample.mvvm.viewmodel.home

import vic.sample.chatuisample.ui.activity.home.adapter.UserItem

interface HomeCallback {
    fun logout()

    fun connectUserToChat(user:UserItem)
}