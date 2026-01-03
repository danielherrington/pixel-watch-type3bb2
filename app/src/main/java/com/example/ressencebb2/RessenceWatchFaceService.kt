package com.example.ressencebb2

import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository

/**
 * Stub Service to satisfy Manifest requirements.
 * The system should use the XML definition via the <property> tag.
 */
class RessenceWatchFaceService : WatchFaceService() {
    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        // This should not be called if WFF property is respected.
        // Returning null or throwing might crash if called.
        // We do minimal implementation just in case.
        throw NotImplementedError("WFF XML should handle rendering.")
    }
}
