package dev.jetlaunch.wallpapercore

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.annotation.CallSuper
import dev.jetlaunch.wallpapercore.entity.IEngine
import dev.jetlaunch.wallpapercore.utils.SizeManager

abstract class SuperWallPaperService: WallpaperService() {

    override fun onCreate() {
        super.onCreate()
        SizeManager.setupMultiplier(resources)
    }

    abstract inner class SuperEngine: WallpaperService.Engine(), IEngine {
        private val  mGameThread : GameThread by lazy { provideGameThread() }
        private val  mScene: SuperScene by  lazy { provideScene() }
        private var canDraw = false

        /**
         * This function called in constructor to init engine
         * it's best place to call @{link SuperEngine#createGameThread(IEngine, Long)}
         */
        abstract fun provideGameThread(): GameThread

        /**
         * This function called when we have positive sized surface
         */
        @CallSuper
        fun onSizeChanged(width: Int, height: Int){
            mScene.setupSize(width, height)
            mScene.setupLayers()
        }

        abstract fun provideScene(): SuperScene

        @CallSuper
        override fun drawFrame() {
            try {
                if (canDraw)
                    surfaceHolder.lockCanvas()?.let { canvas ->
                        mScene.drawFrame(canvas)
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         *This function create GameThread
         * @param engine: actually it's just this
         * @param elapseTime: time for which thread sleep before next call @{link IEngine#drawFrame() }.
         */
        protected fun createGameThread(engine: IEngine, elapseTime: Long = 10L): GameThread {
          return GameThread(engine, elapseTime)
        }

        //region overridet methods

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            mGameThread.safeStart()
            canDraw = true
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            canDraw = false
            mGameThread.safeStop()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if(isVisible){
                mGameThread.safeStart()
            }
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)
            if (width > 0 && height > 0)
                onSizeChanged(width, height)
        }


        override fun onDestroy() {
            super.onDestroy()
            mGameThread.safeStop()
        }

    //endregion

    }
}