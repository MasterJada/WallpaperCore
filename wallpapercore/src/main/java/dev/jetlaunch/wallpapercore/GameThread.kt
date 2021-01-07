package dev.jetlaunch.wallpapercore

import android.os.Handler
import android.os.Looper
import dev.jetlaunch.wallpapercore.entity.IEngine
import org.jetbrains.annotations.TestOnly
import java.util.concurrent.atomic.AtomicBoolean

class GameThread private constructor(private val elapse: Long = 10L) : Thread("Game thread") {
    private lateinit var mHandler: Handler
    private var mEngine: IEngine? = null
    private val mActive = AtomicBoolean(false)


    constructor(engine: IEngine, elapse: Long = 10L) : this(elapse) {
        mEngine = engine
        mHandler = Handler(Looper.getMainLooper())
    }

    @TestOnly
    constructor(engine: IEngine, elapse: Long, handler: Handler) : this(elapse) {
        mEngine = engine
        mHandler = handler
    }

    fun safeStop() {
        interrupt()
    }

    fun safeStart() {
        if (!mActive.getAndSet(true)) {
            start()
        }
    }

    override fun run() {
        while (mActive.get()) {
            try {
                mHandler.run { mEngine?.drawFrame() }
                sleep(elapse)
            } catch (e: InterruptedException) {
                mActive.set(false)
            }
        }
    }

}