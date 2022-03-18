package com.example.droidfleetid.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.droidfleetid.R
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.presentation.DroidFleetViewModel
import com.example.droidfleetid.presentation.MainActivity
import com.example.droidfleetid.presentation.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * A simple [Fragment] subclass.
 * Use the [NavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavigationFragment : Fragment() {

    private var viewModel: DroidFleetViewModel? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var expire: Int? = null

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val EXPIRES = "expires"

        @JvmStatic
        fun newInstance(access_token: String?, refresh_token: String?, expire: Int?) =
            NavigationFragment().apply {
                arguments = Bundle().apply {
                    putString(ACCESS_TOKEN, access_token)
                    putString(REFRESH_TOKEN, refresh_token)
                    if (expire != null) {
                        putInt(EXPIRES, expire)
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accessToken = it.getString(ACCESS_TOKEN)
            refreshToken = it.getString(REFRESH_TOKEN)
            expire = it.getInt(EXPIRES)
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthorizationProperties(accessToken, expire, refreshToken))
        )[DroidFleetViewModel::class.java]

        Log.d("DROIDFLEETID11", "Параметры: $accessToken $refreshToken $expire")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_navigation, container, false)
        val navView: BottomNavigationView = root.findViewById(R.id.nav_view)
        val navController =
            (childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        navView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.reportFragment,
                R.id.mapsFragment,
                R.id.settingsFragment,
                R.id.deviceList
            )
        )
        (activity as MainActivity).setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )


        return root
    }

}