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
        
        val button = android.widget.Button(this).apply {
            text = "Set Watch Face"
            setOnClickListener {
                try {
                    val intent = android.content.Intent(android.app.WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
                        putExtra(
                            android.app.WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                            android.content.ComponentName(this@MainActivity, RessenceWatchFaceService::class.java)
                        )
                    }
                    startActivity(intent)
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
