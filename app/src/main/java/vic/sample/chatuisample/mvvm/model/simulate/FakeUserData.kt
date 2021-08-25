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
            "Mike",
            "Milk",
            "Yang",
            "Pizza",
            "Kevin",
            "Alan"
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
            "Mike@gmail.com",
            "Mikl@gmail.com",
            "Yang@gmail.com",
            "Pizza@gmail.com",
            "Kevin@gmail.com",
            "Alan@gmail.com"
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
            "WOW",
            "I Got You!",
            "Have a nice day!",
        )
    }
}