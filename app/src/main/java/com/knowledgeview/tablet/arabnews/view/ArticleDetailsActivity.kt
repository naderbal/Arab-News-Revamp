package com.knowledgeview.tablet.arabnews.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.knowledgeview.tablet.arabnews.R

class ArticleDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_activity)
        val id = intent.extras.get("entityID") as String
        Toast.makeText(applicationContext, id, Toast.LENGTH_SHORT).show()
    }
}