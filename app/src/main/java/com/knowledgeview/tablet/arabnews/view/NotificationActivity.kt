package com.knowledgeview.tablet.arabnews.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.knowledgeview.tablet.arabnews.R
import com.srpc.independantminds.model.local.Prefs

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notifications_activity)
        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val turnOn = findViewById<TextView>(R.id.turn_on)
        val notNow = findViewById<TextView>(R.id.not_now)
        turnOn.setOnClickListener {
            Prefs.getInstance(applicationContext).setFirstTime(false)
            Prefs.getInstance(applicationContext).setNotification(true)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        notNow.setOnClickListener {
            Prefs.getInstance(applicationContext).setFirstTime(false)
            Prefs.getInstance(applicationContext).setNotification(false)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}