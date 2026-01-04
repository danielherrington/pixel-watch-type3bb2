package com.example.ressencebb2

import android.service.wallpaper.WallpaperService
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.os.Handler
import android.os.Looper

class RessenceWatchFaceService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return RessenceEngine()
    }

    inner class RessenceEngine : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private val drawRunnable = Runnable { draw() }
        private var visible = false

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                handler.post(drawRunnable)
            } else {
                handler.removeCallbacks(drawRunnable)
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            draw()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            visible = false
            handler.removeCallbacks(drawRunnable)
        }

        private fun draw() {
            val holder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                if (canvas != null) {
                    // Draw Green Background to prove Native Service works
                    canvas.drawColor(Color.GREEN)
                    
                    val paint = Paint().apply {
                        color = Color.BLACK
                        textSize = 50f
                        textAlign = Paint.Align.CENTER
                    }
                    canvas.drawText("NATIVE SERVICE", canvas.width / 2f, canvas.height / 2f, paint)
                }
            } catch (e: Exception) {
                // Log error
            } finally {
                if (canvas != null) {
                    try {
                        holder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        // Ignore
                    }
                }
            }
        }
    }
}
