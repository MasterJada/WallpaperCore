package dev.jetlaunch.wallpapercore

import dev.jetlaunch.wallpapercore.utils.Randomizer
import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException

class RandomizerTest {
    @Test(expected = IllegalArgumentException::class)
    fun testIntBetween(){
        val number = Randomizer.getIntBetween(0, 1)
        Assert.assertTrue(number in 0..1)
        Randomizer.getIntBetween(-1 , -2)
    }

}