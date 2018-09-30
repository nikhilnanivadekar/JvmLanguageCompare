package jvm.language.compare.scala

import org.junit.{Assert, Test}

class CanaryTest {

  @Test
  def canary() {
    Assert.assertFalse("This is a Canary Test", false);
  }

}
