package dev.jetlaunch.wallpapercore.entity


interface IEngine {
    /**
     * This function called every tick of thread
     */
    fun drawFrame()
}