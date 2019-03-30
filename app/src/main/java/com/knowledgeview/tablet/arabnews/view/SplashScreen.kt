package com.knowledgeview.tablet.arabnews.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.knowledgeview.tablet.arabnews.R
import com.srpc.independantminds.model.local.Prefs


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val videoView = findViewById<VideoView>(R.id.video_view)
        videoView.setVideoURI(Uri.parse("android.resource://$packageName/"+R.raw.video_splash))
        videoView.setZOrderOnTop(true)
        Handler().postDelayed({
            videoView.start()
        }, 800)

        videoView.setOnCompletionListener {
            if (Prefs.getInstance(applicationContext).getFirstTime()) {
                startActivity(Intent(applicationContext, TutorialActivity::class.java))
            } else startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}