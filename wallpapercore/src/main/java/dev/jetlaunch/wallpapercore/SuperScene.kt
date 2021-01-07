package dev.jetlaunch.wallpapercore

import android.graphics.Canvas
import android.view.MotionEvent
import dev.jetlaunch.wallpapercore.entity.ILayer
import java.lang.IllegalArgumentException

abstract class SuperScene {
    protected val layers = ArrayList<ILayer>()
    protected var sceneWidth = 0
    protected var sceneHeight = 0

    /**
     * This method used to setup layers
     */
    abstract fun setupLayers()

    fun onTouchEvent(event: MotionEvent?){
        layers.forEach {
            it.onTouch(event)
        }
    }

    fun setupSize(w: Int, h: Int){
        if(w <= 0 || h <= 0)
            throw IllegalArgumentException("Arguments must be > 0")
        sceneHeight = h
        sceneWidth = w
    }

    fun drawFrame(canvas: Canvas){
        layers.forEach {
            it.draw(canvas)
        }
    }
}