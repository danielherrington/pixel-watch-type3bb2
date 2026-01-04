package com.example.ressencebb2

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            gravity = android.view.Gravity.CENTER
        }

        val textView = TextView(this).apply {
            text = "Wear OS 6 (Preview)\nForce Installer"
            gravity = android.view.Gravity.CENTER
            setPadding(0, 0, 0, 20)
        }
        
        // Diagnostic Button
        val button = android.widget.Button(this).apply {
            text = "Diagnose & Set"
            setOnClickListener {
                textView.text = "Checking Service..."
                try {
                    val comp = android.content.ComponentName(this@MainActivity, RessenceWatchFaceService::class.java)
                    val statusIntent = android.content.Intent(android.service.wallpaper.WallpaperService.SERVICE_INTERFACE)
                    statusIntent.component = comp
                    
                    val resolveInfo = packageManager.resolveService(statusIntent, android.content.pm.PackageManager.GET_META_DATA)
                    
                    if (resolveInfo == null) {
                        textView.text = "FAIL: Service NOT found.\nPkg: ${comp.packageName}\nCls: ${comp.className}"
                    } else {
                        textView.text = "SUCCESS: Service Found!\nLaunching Picker..."
                        // Launch Intent
                         val intent = android.content.Intent(android.app.WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
                            putExtra(
                                android.app.WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                comp
                            )
                        }
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    textView.text = "Error:\n${e.message}"
                }
            }
        }

        container.addView(textView)
        container.addView(button)
        setContentView(container)
    }
}
