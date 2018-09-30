package jvm.language.compare.scala;

import org.junit.Assert;
import org.junit.Test;

public class CanaryTest
{
    @Test
    public void canary()
    {
        Assert.assertFalse("This is a Canary Test", false);
    }

    @Test
    public void canary_fails()
    {
        Assert.assertFalse("This is a Canary Test", true);
    }
}
