package dev.jetlaunch.wallpapercore.entity

import android.graphics.Canvas

interface ILayer {
    fun init(width: Int, height: Int)
    fun draw(canvas: Canvas)
}