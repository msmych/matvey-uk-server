package uk.matvey

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AppTest {

    @Test
    fun appHasAGreeting() {
        val classUnderTest = App()
        assertThat(classUnderTest.greeting).isNotNull.withFailMessage("app should have a greeting")
    }
}
