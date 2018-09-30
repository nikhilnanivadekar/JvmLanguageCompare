package jvm.language.compare.kotlin

import org.junit.Assert
import org.junit.Test

class CanaryTest {

    @Test
    fun canaryTest() {
        Assert.assertFalse("This is a Canary Test", false)
    }
}
