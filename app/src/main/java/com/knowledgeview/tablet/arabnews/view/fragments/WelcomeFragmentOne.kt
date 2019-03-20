package com.knowledgeview.tablet.arabnews.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProviders
import com.knowledgeview.tablet.arabnews.R
import com.knowledgeview.tablet.arabnews.di.ViewModelFactory
import com.knowledgeview.tablet.arabnews.viewmodel.SharedViewModel
import com.srpc.independantminds.model.local.Prefs
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class WelcomeFragmentOne : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.welcome_fragment_one, container, false)
        val acceptCheckBox = view.findViewById<CheckBox>(R.id.accept_checkbox)
        sharedViewModel = ViewModelProviders.of(activity!!, viewModelFactory)
                .get(SharedViewModel::class.java)
        acceptCheckBox.setOnCheckedChangeListener { _, isChecked ->
            Prefs.getInstance(context!!).setAccept(isChecked)
            sharedViewModel.data.postValue(isChecked)
        }
        return view
    }
}