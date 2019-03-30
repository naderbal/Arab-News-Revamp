package com.knowledgeview.tablet.arabnews.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.view.adapters.TutorialPagerAdapter
import com.knowledgeview.tablet.arabnews.viewmodel.SharedViewModel
import com.srpc.independantminds.model.local.Prefs
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.tutorial_activity.*
import me.relex.circleindicator.CircleIndicator
import javax.inject.Inject


class TutorialActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>


    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tutorial_activity)
        AndroidInjection.inject(this)
        val tutorialPager = findViewById<ViewPager>(R.id.tutorial_pager)
        tutorialPager.adapter = TutorialPagerAdapter(supportFragmentManager)
        val indicator = findViewById<CircleIndicator>(R.id.indicator)
        sharedViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SharedViewModel::class.java)
        sharedViewModel.getData().observe(this, Observer { data ->
            next.isEnabled = data != null && data
        })
        indicator.setViewPager(tutorialPager)
        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val back = findViewById<TextView>(R.id.back)
        val next = findViewById<TextView>(R.id.next)
        back.setOnClickListener {
            if (position != 0) {
                position -= 1
                tutorialPager.currentItem = position
            }
        }
        next.setOnClickListener {
            if (Prefs.getInstance(applicationContext).getAccept()) {
                if (position != 1) {
                    position += 1
                    tutorialPager.currentItem = position
                } else {
                    startActivity(Intent(applicationContext, NotificationActivity::class.java))
                    finish()
                }
            } else Toast.makeText(applicationContext, "Please accept the license and agreement"
                    , Toast.LENGTH_SHORT).show()
        }
        tutorialPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(p: Int) {
                position = p
            }

        })

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }
}