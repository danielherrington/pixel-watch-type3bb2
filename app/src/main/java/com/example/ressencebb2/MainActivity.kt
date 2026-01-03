package com.example.ressencebb2

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "Ressence BB2 Installed!\nLong press current face to switch."
        textView.gravity = android.view.Gravity.CENTER
        setContentView(textView)
    }
}
