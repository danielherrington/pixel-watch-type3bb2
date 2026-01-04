package com.example.ressencebb2

import android.view.SurfaceHolder
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.style.CurrentUserStyleRepository
import java.time.ZonedDateTime

class RessenceWatchFaceService : WatchFaceService() {

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: androidx.wear.watchface.WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {

        // Log.d("Ressence", "Creating Watch Face Engine")

        val renderer = object : Renderer.CanvasRenderer(
            surfaceHolder,
            currentUserStyleRepository,
            watchState,
            CanvasType.HARDWARE, // Use Hardware Canvas
            16L
        ) {
            val paint = Paint().apply {
                color = Color.MAGENTA
                textSize = 40f
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
            }
            
            val bgPaint = Paint().apply {
                color = Color.BLACK
            }

            override fun render(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
                // Draw Full code face
                canvas.drawRect(bounds, bgPaint)
                canvas.drawText("JETPACK FACE", bounds.centerX().toFloat(), bounds.centerY().toFloat(), paint)
            }

            override fun renderHighlightLayer(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
                // No-op
            }
        }

        return WatchFace(
            WatchFaceType.ANALOG,
            renderer
        )
    }
}
