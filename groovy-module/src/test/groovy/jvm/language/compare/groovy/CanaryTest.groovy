package jvm.language.compare.groovy

import org.junit.Assert
import org.junit.Test

class CanaryTest {

    @Test
    void canary() {
        Assert.assertFalse("This is a Canary Test-1", false)
    }
}
