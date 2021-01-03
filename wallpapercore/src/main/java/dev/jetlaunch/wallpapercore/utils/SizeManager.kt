package dev.jetlaunch.wallpapercore.utils

import android.content.res.Resources
import kotlin.math.roundToInt

object SizeManager {
    private var multiplier = 1F
    var velocityMultiplier = 1F

    fun setupMultiplier(resources: Resources) {
        when (resources.displayMetrics.density) {
            1.5F, 1F -> {
                multiplier = 0.3F
                velocityMultiplier = 0.2F
            }
            else -> {
                multiplier = 1F
                velocityMultiplier = 1F
            }
        }
    }

    fun convert(v: Int): Int {
        return (v * multiplier).roundToInt()
    }

    fun convert(v: Float): Float {
        return v * multiplier
    }
}

fun Int.toDensity(): Int {
    return SizeManager.convert(this)
}

fun Float.toDensity(): Float {
    return SizeManager.convert(this)
}