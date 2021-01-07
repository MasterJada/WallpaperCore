package dev.jetlaunch.wallpapercore

import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

class SceneTests {

    @Test(expected = IllegalArgumentException::class)
    fun testSetupSize(){
        val scene = object :SuperScene(){
            override fun setupLayers() {

            }
        }

        scene.setupSize(0, 0)
    }

}