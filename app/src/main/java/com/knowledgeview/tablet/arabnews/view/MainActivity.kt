package com.knowledgeview.tablet.arabnews.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.models.data.ParentSection
import com.knowledgeview.tablet.arabnews.view.adapters.ExpandableListMenuAdapter
import com.knowledgeview.tablet.arabnews.view.fragments.*
import com.knowledgeview.tablet.arabnews.view.fragments.face.FaceGalleryFragment
import com.knowledgeview.tablet.arabnews.view.fragments.opinions.OpinionsListFragment
import com.knowledgeview.tablet.arabnews.view.fragments.photosGallery.PhotoGalleryFragment
import com.knowledgeview.tablet.arabnews.view.fragments.videoGallery.VideoGalleryFragment
import com.knowledgeview.tablet.arabnews.viewmodel.SectionListingViewModel
import com.srpc.independantminds.model.local.Prefs
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>


    private lateinit var sectionListingViewModel: SectionListingViewModel

    // views
    lateinit var layoutGreen: RelativeLayout
    lateinit var layoutWhite: RelativeLayout
    lateinit var ivGreenLogo: ImageView
    lateinit var ivWhiteLogo: ImageView
    lateinit var greenTitle: TextView
    lateinit var whiteTitle: TextView
    lateinit var bottomTabs: BottomNavigationView

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var position = 0
    private var readingListPosition = 1
    private var videoListPosition = 1
    private var opinionListPosition = 1
    private var photosListPosition = 1
    private val broadcastAction = "in.app.notifications.arabnews"
    private var notificationReceiver: NotificationBroadcastReceiver? = null
    private var menus = arrayListOf<ParentSection>()
    private var expandable: ExpandableListMenuAdapter? = null
    private var notificationLayout: RelativeLayout? = null
    private var notificationTitle: TextView? = null
    private var notificationText: TextView? = null
    private var articleID: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        greenTitle = findViewById(R.id.tv_green_title)
        whiteTitle = findViewById(R.id.tv_white_title)
        ivGreenLogo = findViewById(R.id.iv_logo)
        ivWhiteLogo = findViewById(R.id.iv_white_logo)
        val close = findViewById<ImageView>(R.id.close)
        notificationLayout = findViewById(R.id.breaking_news)
        notificationTitle = findViewById(R.id.breaking_news_title)
        notificationText = findViewById(R.id.breaking_news_content)

        close.setOnClickListener {
            notificationLayout!!.visibility = View.GONE
        }
        notificationTitle!!.setOnClickListener {
            if (!articleID.isNullOrEmpty()) {
                    //go to the article details page
            }
        }
        notificationText!!.setOnClickListener {
            if (!articleID.isNullOrEmpty()) {
                //go to the article details page
            }
        }

        layoutGreen = findViewById(R.id.layout_green)
        layoutWhite = findViewById(R.id.layout_white)

        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        AndroidInjection.inject(this)
        val footerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.footer_menu_one, null, false)
        val readingListView = footerView.findViewById<RelativeLayout>(R.id.reading_list_view)
//        val videosView = footerView.findViewById<RelativeLayout>(R.id.videos_layout)
        bottomTabs = findViewById(R.id.bottom_tabs)
        bottomTabs.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.reading_list -> {
                    if (position != readingListPosition) {
                        position = readingListPosition
                        openFragment(ReadingListFragment())
                        setToolbarGreenEnabled(true, getString(R.string.reading_list))
                        expandable!!.getPosition(-1)
                    }
                }
                R.id.home -> {
                    if (position != 0) {
                        position = 0
                        expandable!!.getPosition(position)
                        openFragment(getHomeFragment())
                        setToolbarGreenEnabled(false, null)
                    }
                }
                R.id.videos -> {
                    if (position != videoListPosition) {
                        position = videoListPosition
                        openFragment(VideosFragment())
                        setToolbarGreenEnabled(true, getString(R.string.videos))
                        expandable!!.getPosition(videoListPosition)

                    }
                }
            }
            true
        }
        val burgerIcon = findViewById<ImageView>(R.id.burger_icon)
        val burgerIconGreen = findViewById<ImageView>(R.id.burger_icon_green)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val listMenu = findViewById<ExpandableListView>(R.id.list_menu)
        sectionListingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SectionListingViewModel::class.java)
        readingListView.setOnClickListener {
            if (position != readingListPosition) {
                position = readingListPosition
                bottomTabs.selectedItemId = R.id.reading_list
                openFragment(ReadingListFragment())
                setToolbarGreenEnabled(true, getString(R.string.reading_list))
                expandable!!.getPosition(-1)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        listMenu.addFooterView(footerView)
        val footerViewTwo = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.footer_menu_two, null, false)
        val notifications = footerViewTwo.findViewById<SwitchCompat>(R.id.notifications)
        notifications.isChecked = Prefs.getInstance(applicationContext).getNotification()
        notifications.setOnCheckedChangeListener { _, isChecked ->
            Prefs.getInstance(applicationContext).setNotification(isChecked)
        }
        listMenu.addFooterView(footerViewTwo)
        val headerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.header_menu, null, false)
        val pdf = headerView.findViewById<TextView>(R.id.pdf_read)
        pdf.setOnClickListener {
            startActivity(Intent(applicationContext, PDFActivity::class.java))
        }
        listMenu.addHeaderView(headerView)
        sectionListingViewModel.getSections().observe(this, Observer { sections ->
            if (sections != null) {
                if (sections.data != null && sections.data.isNotEmpty()) {
                    menus.clear()
                    menus.add(ParentSection("HOME", null, null))
                    menus.addAll(sections.data)
                    menus.add(ParentSection("OPINION", null, null))
                    opinionListPosition = menus.size - 1
                    menus.add(ParentSection("VIDEOS", null, null))
                    videoListPosition = menus.size - 1
                    menus.add(ParentSection("PHOTOS", null, null))
                    photosListPosition = menus.size - 1
                    readingListPosition = menus.size
                    expandable = ExpandableListMenuAdapter(applicationContext, menus)
                    listMenu.setAdapter(expandable)
                    openFragment(getHomeFragment())
                }
            }
        })
        burgerIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else drawerLayout.openDrawer(GravityCompat.START)
        }
        burgerIconGreen.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else drawerLayout.openDrawer(GravityCompat.START)
        }
        notificationReceiver = NotificationBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(broadcastAction)
        registerReceiver(notificationReceiver, intentFilter)
        listMenu.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val bundle = Bundle()
            val parentSection = menus[groupPosition].getChildren()!![childPosition]
            bundle.putString("tid", parentSection.getTid())
            val title = parentSection.getTitle()
            if (title.equals("The face")) {
                val fragment = FaceGalleryFragment()
                fragment.arguments = bundle
                openFragment(fragment)
                drawerLayout.closeDrawer(GravityCompat.START)
                setToolbarGreenEnabled(false, "The Face")
                layoutWhite.visibility = View.VISIBLE
                layoutGreen.visibility = View.GONE
            } else {
                val screenListingFragment = ScreenListingFragment()
                screenListingFragment.arguments = bundle
                openFragment(screenListingFragment)
                drawerLayout.closeDrawer(GravityCompat.START)
                layoutWhite.visibility = View.VISIBLE
                layoutGreen.visibility = View.GONE
            }
            false
        }
        listMenu.setOnGroupClickListener { _, _, groupPosition, _ ->
            if (position != groupPosition) {
                position = groupPosition
                var closeDrawer = false
                when {
                    groupPosition == videoListPosition -> {
                        openFragment(VideoGalleryFragment())
                        layoutWhite.visibility = View.GONE
                        //greenTitle.text = getString(R.string.videos)
                        layoutGreen.visibility = View.VISIBLE
                        expandable!!.getPosition(videoListPosition)
                        bottomTabs.selectedItemId=R.id.videos
                        closeDrawer = true
                    }
                    groupPosition == 0 -> {
                        openFragment(getHomeFragment())
                        bottomTabs.selectedItemId = R.id.home
                        setToolbarGreenEnabled(false, null)
                        closeDrawer = true
                    }
                    position==opinionListPosition -> {
                        openFragment(OpinionsListFragment())
                        setToolbarGreenEnabled(true, getString(R.string.opinions))
                        closeDrawer = true
                    }
                    position==photosListPosition -> {
                        openFragment(PhotoGalleryFragment())
                        closeDrawer = true
                    }

                    menus[groupPosition].getChildren().isNullOrEmpty() -> {
                        val screenListingFragment = ScreenListingFragment()
                        screenListingFragment.listPositionListener = object  : ListPostionListener {
                            override fun onListPositionChanged(position: Int) {
                                setToolbarGreenEnabled(position >= 1, menus[groupPosition].getTitle())
                            }
                        }
                        val bundle = Bundle()
                        bundle.putString("tid", menus[groupPosition].getTid())
                        screenListingFragment.arguments = bundle
                        openFragment(screenListingFragment)
                        setToolbarGreenEnabled(false, menus[groupPosition].getTitle())
                        closeDrawer = true
                    }
                }
                expandable!!.positionClicked = groupPosition
                if (closeDrawer) drawerLayout.closeDrawer(GravityCompat.START)
            } else { // saudi arabia
                val screenListingFragment = ScreenListingFragment()
                screenListingFragment.listPositionListener = object  : ListPostionListener {
                    override fun onListPositionChanged(position: Int) {
                        setToolbarGreenEnabled(position >= 1, menus[groupPosition].getTitle())
                    }
                }
                val bundle = Bundle()
                bundle.putString("tid", menus[groupPosition].getTid())
                screenListingFragment.arguments = bundle
                openFragment(screenListingFragment)
                setToolbarGreenEnabled(false, menus[groupPosition].getTitle())
            }
            false
        }
    }

    private fun setToolbarGreenEnabled(isEnabled : Boolean, title: String?) {
        if (!isEnabled) {
            layoutWhite.visibility = View.VISIBLE
            layoutGreen.visibility = View.GONE
            if (title != null) {
                whiteTitle.visibility = View.VISIBLE
                whiteTitle.text = title
                ivWhiteLogo.visibility = View.GONE
            } else {
                ivWhiteLogo.visibility = View.VISIBLE
                whiteTitle.visibility = View.GONE
            }
        } else {
            layoutWhite.visibility = View.GONE
            layoutGreen.visibility = View.VISIBLE
            if (title != null) {
                greenTitle.text = title
                ivGreenLogo.visibility = View.GONE
                greenTitle.visibility = View.VISIBLE
            } else {
                ivGreenLogo.visibility = View.VISIBLE
                greenTitle.visibility = View.GONE
            }
        }
    }

    private fun getHomeFragment() : HomeFragment {
        val homeFragment = HomeFragment()
        homeFragment.listPositionListener = object : ListPostionListener {
            override fun onListPositionChanged(position: Int) {
                setToolbarGreenEnabled(position >= 1, null)
                bottomTabs.visibility = if (position >= 1) View.VISIBLE else View.GONE
            }
        }
        return homeFragment
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    private class NotificationBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val activity = context as MainActivity
            activity.notificationLayout!!.visibility = View.VISIBLE
            if (intent!!.hasExtra("title")) {
                activity.notificationTitle!!.text = intent.getStringExtra("title")
            }
            if (intent.hasExtra("message")) {
                activity.notificationText!!.text = intent.getStringExtra("message")
            }
            if (intent.hasExtra("entityID")) {
                activity.articleID = intent.getStringExtra("entityID")
            }
            if (intent.hasExtra("type")) {
                if (intent.getStringExtra("type") == "default") {
                    activity.notificationLayout!!.setBackgroundColor(
                            ContextCompat.getColor(context, R.color.grey)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(notificationReceiver)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment).commit()
    }


}
