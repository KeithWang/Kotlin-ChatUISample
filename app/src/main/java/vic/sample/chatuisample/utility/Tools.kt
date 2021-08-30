package vic.sample.chatuisample.utility

import androidx.core.util.PatternsCompat

class Tools {
    fun checkEmail(email: String): Boolean {
        return if (email.contains('@')) {
            PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isBlank()
        }
    }
}