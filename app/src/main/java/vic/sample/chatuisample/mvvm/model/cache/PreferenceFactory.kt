package vic.sample.chatuisample.mvvm.model.cache

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceFactory<T>(val key: String, val default: T, val context: Context)
    : ReadWriteProperty<Any?, T> {

    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharedPreferences(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharedPreferences(key, value)
    }

    private fun putSharedPreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("SharedPreferences can't be save this type")
        }.apply()
    }

    private fun getSharedPreferences(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("SharedPreferences can't be get this type")
        }!!
        @Suppress("UNCHECKED_CAST")
        return res as T
    }

}