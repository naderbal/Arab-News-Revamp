package com.knowledgeview.tablet.arabnews.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.knowledgeview.tablet.arabnews.R
import com.srpc.independantminds.model.local.Prefs


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val handler = Handler()
        handler.postDelayed({
            if (Prefs.getInstance(applicationContext).getFirstTime()) {
                startActivity(Intent(applicationContext, TutorialActivity::class.java))
            } else startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 1500)
    }
}