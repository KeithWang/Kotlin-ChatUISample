package vic.sample.chatuisample.mvvm.model.simulate


object FakeUserData {
    fun getUserName(): Array<String> {
        return arrayOf(
            "Lisa",
            "Nick",
            "Amy",
            "Hamburger",
            "Ham",
            "Keith",
            "Lisa",
            "Nick",
            "Amy",
            "Hamburger",
            "Ham",
            "Keith"
        )
    }

    fun getUserEmail(): Array<String> {
        return arrayOf(
            "Lisa@gmail.com",
            "Nick@gmail.com",
            "Amy@gmail.com",
            "Hamburger@gmail.com",
            "Ham@gmail.com",
            "Keith@gmail.com",
            "Lisa@gmail.com",
            "Nick@gmail.com",
            "Amy@gmail.com",
            "Hamburger@gmail.com",
            "Ham@gmail.com",
            "Keith@gmail.com"
        )
    }

    fun getUserResponse(): Array<String> {
        return arrayOf(
            "Hello",
            "Hi",
            "Good Morning",
            "Good Night",
            "Good",
            "Nice",
            "Good to see you again!",
            "Nice to see you!",
        )
    }
}