package dev.jetlaunch.wallpapercore

import android.os.Handler
import dev.jetlaunch.wallpapercore.entity.IEngine
import org.junit.Assert
import org.junit.Test

open class GameThreadTest {


    @Test
    fun startStopTest(){
        var counter = 0

       val thread = GameThread(object : IEngine{
            override fun drawFrame() {
               counter++
                if (counter == 2)
                    Assert.fail("work must be stopped")
            }

        }, 100L, Handler())
        thread.safeStart()
        thread.safeStop()
    }

    @Test(expected = IllegalThreadStateException::class)
    fun startStopStart(){
        val thread = GameThread(object : IEngine{
            override fun drawFrame() {
                //
            }

        }, 10L, Handler())

        thread.safeStart()
        Thread.sleep(20L)
        thread.safeStop()
        Thread.sleep(20L)
        thread.safeStart()

    }

    @Test
    fun drawTest(){
        var counter = 0
        val thread = GameThread(object : IEngine{
            override fun drawFrame() {
                counter ++
            }

        }, 10L, Handler())
        thread.safeStart()
        Thread.sleep(220)
        thread.safeStop()
        Assert.assertEquals(20, counter)
    }
}