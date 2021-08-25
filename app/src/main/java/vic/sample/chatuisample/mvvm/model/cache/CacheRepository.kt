package vic.sample.chatuisample.mvvm.model.cache

import android.content.Context

class CacheRepository(val context: Context) {

    companion object {
        private const val USER_ID_TAG = "USER_ID_TAG"
        private const val USER_NAME_TAG = "USER_NAME_TAG"
        private const val USER_EMAIL_TAG = "USER_EMAIL_TAG"
    }

    var userID by PreferenceFactory(
        USER_ID_TAG, "", context
    )

    var userName by PreferenceFactory(
        USER_NAME_TAG, "", context
    )

    var userEmail by PreferenceFactory(
        USER_EMAIL_TAG, "", context
    )

    fun clearAll() {
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).edit().clear()
            .apply()
    }
}