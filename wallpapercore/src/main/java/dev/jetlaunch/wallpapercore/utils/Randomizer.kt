package dev.jetlaunch.wallpapercore.utils

import kotlin.random.Random

object Randomizer {
    private val random = Random(2)

    fun getIntBetween(from: Int, until: Int): Int {
        return random.nextInt(from, until)
    }

    fun getNextFloat(from: Float): Float{
        return from + random.nextFloat()
    }
}