package com.knowledgeview.tablet.arabnews.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.knowledgeview.tablet.arabnews.view.fragments.WelcomeFragmentOne
import com.knowledgeview.tablet.arabnews.view.fragments.WelcomeFragmentTwo

class TutorialPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0)
            WelcomeFragmentOne()
        else WelcomeFragmentTwo()
    }

    override fun getCount(): Int {
        return 1
    }
}