package com.example.ressencebb2

import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.style.CurrentUserStyleRepository
import java.time.ZonedDateTime
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class RessenceWatchFaceService : WatchFaceService() {

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: androidx.wear.watchface.WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        
        // internal simple renderer
        val renderer = object : Renderer.CanvasRenderer(
            surfaceHolder,
            currentUserStyleRepository,
            watchState,
            CanvasType.HARDWARE,
            16L
        ) {
            val paint = Paint().apply {
                color = Color.RED
                textSize = 50f
                textAlign = Paint.Align.CENTER
            }

            override fun render(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
                canvas.drawColor(Color.BLACK)
                canvas.drawText("CODE FACE", bounds.centerX().toFloat(), bounds.centerY().toFloat(), paint)
            }

            override fun renderHighlightLayer(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
                // No highlight needed
            }
        }

        return WatchFace(
            WatchFaceType.ANALOG,
            renderer
        )
    }
}
