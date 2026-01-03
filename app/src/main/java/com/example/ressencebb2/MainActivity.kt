package com.example.ressencebb2

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "DEBUG MODE\nMinimal Red Face Active\nCheck picker."
        textView.gravity = android.view.Gravity.CENTER
        setContentView(textView)
    }
}
