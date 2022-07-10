package com.example.droidfleetid.presentation.fragments

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.droidfleetid.DFApp
import com.example.droidfleetid.R
import com.example.droidfleetid.presentation.DroidFleetViewModel
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val viewModel: DroidFleetViewModel by activityViewModels()


    private val component by lazy {
        (requireActivity().application as DFApp).component
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        component.inject(this)
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        findPreference<Preference>("exit_button")?.setOnPreferenceClickListener {
            Log.d("shared", "shared")
            sharedPreferences.edit().clear().apply()
            viewModel.isUserLoggedIn = false
            viewModel.deleteEntityList()
            requireActivity().supportFragmentManager.popBackStack()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.droid_fleet_container, LoginFragment()).commit()
            true
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view.setBackgroundColor(Color.WHITE)
        return view
    }


}
