package jvm.language.compare.java;

import org.junit.Assert;
import org.junit.Test;

public class CanaryTest
{
    @Test
    public void canary()
    {
        Assert.assertFalse("This is a Canary Test-1", false);
    }

    @Test
    public void canaryFails()
    {
        Assert.assertFalse("This is a Canary Test-2", true);
    }
}
