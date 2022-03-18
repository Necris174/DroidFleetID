package com.example.droidfleetid.presentation.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.droidfleetid.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}