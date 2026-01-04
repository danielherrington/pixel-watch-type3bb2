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
            CanvasType.HARDWARE,
            16L
        ) {
            // Assets
            private lateinit var bgMain: android.graphics.Bitmap
            private lateinit var bgHour: android.graphics.Bitmap
            private lateinit var handHour: android.graphics.Bitmap
            private lateinit var bgRunner: android.graphics.Bitmap
            private lateinit var handRunner: android.graphics.Bitmap
            private lateinit var bgDay: android.graphics.Bitmap
            private lateinit var bgTemp: android.graphics.Bitmap
            private lateinit var handSmall: android.graphics.Bitmap
            
            // Layout (Scale 450x450 coord system to actual bounds)
            private val referenceSize = 450f
            
            init {
                // Load Bitmaps
                val res = applicationContext.resources
                bgMain = loadBitmap(res, R.drawable.bg_main_minute_disc, 450, 450)
                bgHour = loadBitmap(res, R.drawable.bg_subdial_hours, 160, 160)
                handHour = loadBitmap(res, R.drawable.hand_hour, 160, 160)
                bgRunner = loadBitmap(res, R.drawable.bg_subdial_runner, 120, 120)
                handRunner = loadBitmap(res, R.drawable.hand_runner, 120, 120)
                bgDay = loadBitmap(res, R.drawable.bg_subdial_day, 120, 120)
                bgTemp = loadBitmap(res, R.drawable.bg_subdial_temp, 100, 100)
                handSmall = loadBitmap(res, R.drawable.hand_small, 20, 60) // approx size correction if needed
            }

            private fun loadBitmap(res: android.content.res.Resources, id: Int, w: Int, h: Int): android.graphics.Bitmap {
                 val drawable = res.getDrawable(id, null)
                 val bitmap = android.graphics.Bitmap.createBitmap(w, h, android.graphics.Bitmap.Config.ARGB_8888)
                 val canvas = Canvas(bitmap)
                 drawable.setBounds(0, 0, w, h)
                 drawable.draw(canvas)
                 return bitmap
            }

            override fun render(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
                // Clear background
                canvas.drawColor(Color.BLACK)
                
                // Calculations
                val scale = bounds.width().toFloat() / referenceSize
                val cx = bounds.exactCenterX()
                val cy = bounds.exactCenterY()
                
                val hr = zonedDateTime.hour
                val min = zonedDateTime.minute
                val sec = zonedDateTime.second
                val nanos = zonedDateTime.nano
                val secFrac = sec + (nanos / 1_000_000_000f)
                
                // ROCS Logic
                // 1. System Rotation (Main Disc + all subdials revolve around center)
                // The main disc is the MINUTES track. 
                val systemRotation = (min + secFrac/60f) * 6f 
                
                canvas.save()
                canvas.translate(cx, cy)
                canvas.scale(scale, scale)
                
                // ROTATE ENTIRE SYSTEM
                canvas.rotate(systemRotation)
                
                // 1. Draw Main Minute Disc (Centered)
                // Coordinates: 0,0 is center relative to canvas translate
                drawBitmapCentered(canvas, bgMain, 0f, 0f)
                
                // 2. Subdials
                // They are fixed positions ON the main disc, but they counter-rotate to stay upright?
                // WFF XML said: rotation = -systemRotation
                
                // -- HOUR SUBDIAL (x=105, y=245 in 450px grid. Center is 225,225)
                // Relative X = 105 - 225 = -120 + (160/2) = -120 + 80 = -40? 
                // Wait, XML: <Group x="105" y="245" width="160" height="160">
                // Center of subdial = 105 + 80 = 185, 245 + 80 = 325.
                // Relative to 225,225: dx = 185-225 = -40, dy = 325-225 = 100.
                drawSubdial(canvas, bgHour, handHour, -40f, 100f, -systemRotation, ((hr % 12) + min/60f) * 30f)
                
                // -- RUNNER (180s) (x=165, y=80, w=120) -> Center(165+60=225, 80+60=140)
                // Relative: dx = 0, dy = 140-225 = -85
                // Rotation: (UTC % 180000) / 500 -> 360 deg every 180s.
                // 180s = 3 mins. 
                val runnerRot = ((zonedDateTime.toInstant().toEpochMilli() % 180000) / 180000f) * 360f
                drawSubdial(canvas, bgRunner, handRunner, 0f, -85f, -systemRotation, runnerRot)
                
                // -- DAY (x=300, y=180, w=120) -> Center(360, 240)
                // Relative: dx = 360-225=135, dy = 240-225=15
                // Rotation: (Day - 1) * 51.4 (7 days)
                val dayRot = (zonedDateTime.dayOfWeek.value - 1) * (360f/7f)
                drawSubdial(canvas, bgDay, handSmall, 135f, 15f, -systemRotation, dayRot)

                // -- TEMP (x=250, y=300, w=100) -> Center(300, 350)
                // Relative: dx=75, dy=125
                // Rotation: Demo value for battery/temp (0 deg for now)
                drawSubdial(canvas, bgTemp, handSmall, 75f, 125f, -systemRotation, 0f)

                canvas.restore()
            }
            
            private fun drawBitmapCentered(c: Canvas, bmp: android.graphics.Bitmap, dx: Float, dy: Float) {
                c.drawBitmap(bmp, dx - bmp.width/2f, dy - bmp.height/2f, null)
            }
            
            private fun drawSubdial(c: Canvas, bg: android.graphics.Bitmap, hand: android.graphics.Bitmap, 
                                  dx: Float, dy: Float, 
                                  counterRot: Float, handRot: Float) {
                c.save()
                c.translate(dx, dy)
                
                // Counter-rotate background so it stays upright relative to screen
                c.rotate(counterRot) 
                drawBitmapCentered(c, bg, 0f, 0f)
                
                // Draw Hand
                c.rotate(handRot)
                drawBitmapCentered(c, hand, 0f, 0f)
                
                c.restore()
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
