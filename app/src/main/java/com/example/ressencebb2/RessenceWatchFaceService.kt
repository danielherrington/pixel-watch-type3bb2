package com.example.ressencebb2

import android.service.wallpaper.WallpaperService
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.view.SurfaceHolder

class RessenceWatchFaceService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return RessenceEngine()
    }

    inner class RessenceEngine : Engine() {
        
        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            draw()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                draw()
            }
        }

        private fun draw() {
            val holder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                if (canvas != null) {
                    // Draw BLUE Background - One Shot, No Loop
                    canvas.drawColor(Color.BLUE)
                    
                    val paint = Paint().apply {
                        color = Color.WHITE
                        textSize = 40f
                        textAlign = Paint.Align.CENTER
                        isAntiAlias = true
                    }
                    canvas.drawText("NATIVE BLUE", canvas.width / 2f, canvas.height / 2f, paint)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        holder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
