package dev.jetlaunch.wallpapercore

import android.os.Handler
import android.os.Looper
import dev.jetlaunch.wallpapercore.entity.IEngine
import java.util.concurrent.atomic.AtomicBoolean

class GameThread(private val elapse: Long = 10L ): Thread("Game thread") {
    private val mainHandler = Handler(Looper.getMainLooper())
    private var mEngine: IEngine? = null
    private val mActive = AtomicBoolean(false)

    constructor(engine: IEngine, elapse: Long = 10L): this(elapse){
        mEngine = engine
    }


    fun safeStop(){
        interrupt()
    }

    fun safeStart(){
      if(!mActive.getAndSet(true)){
          start()
      }
    }

    override fun run() {
        while (mActive.get()){
            try {
                mainHandler.run { mEngine?.drawFrame() }
                sleep(elapse)
            }catch (e: InterruptedException){
                mActive.set(false)
            }
        }
    }

}