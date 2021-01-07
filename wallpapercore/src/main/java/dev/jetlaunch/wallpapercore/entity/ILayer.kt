package dev.jetlaunch.wallpapercore.entity

import android.graphics.Canvas
import android.view.MotionEvent

interface ILayer {
    fun init(width: Int, height: Int)
    fun draw(canvas: Canvas)
    fun onTouch(motionEvent: MotionEvent?)
}