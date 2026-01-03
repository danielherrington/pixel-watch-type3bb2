package com.example.ressencebb2

import android.service.wallpaper.WallpaperService

/**
 * Native Stub Service.
 * 
 * We use the platform WallpaperService instead of the Jetpack WatchFaceService
 * to avoid complex lifecycle requirements. This class exists ONLY to:
 * 1. Satisfy the manifest "service" requirement for the Picker.
 * 2. NOT crash when the system probes it.
 *
 * The <property android:name="com.google.android.wearable.watchface.format" ... />
 * in the manifest will take precedence for actual rendering.
 */
class RessenceWatchFaceService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return object : Engine() {
            // A dummy engine that does nothing.
            // The system uses the XML definition, so this engine is never actually shown
            // unless the XML property is ignored (which shouldn't happen on supported devices).
        }
    }
}
