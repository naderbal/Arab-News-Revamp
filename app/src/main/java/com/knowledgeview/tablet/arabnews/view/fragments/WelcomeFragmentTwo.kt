package com.knowledgeview.tablet.arabnews.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.knowledgeview.tablet.arabnews.R

class WelcomeFragmentTwo : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.welcome_fragment_two, container, false)
        return view
    }
}