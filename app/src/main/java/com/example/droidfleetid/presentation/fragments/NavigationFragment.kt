package com.example.droidfleetid.presentation.fragments

import android.content.Context
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
import com.example.domain.entity.AuthorizationProperties
import com.example.droidfleetid.DFApp
import com.example.droidfleetid.R
import com.example.droidfleetid.presentation.DroidFleetViewModel
import com.example.droidfleetid.presentation.MainActivity
import com.example.droidfleetid.presentation.SelectedDevice
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 * Use the [NavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavigationFragment : Fragment() {


    private lateinit var viewModel: DroidFleetViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as DFApp).component
    }

    private lateinit var accessToken: String
    private lateinit var refreshToken: String
    private var expire by Delegates.notNull<Int>()

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val EXPIRES = "expires"


        @JvmStatic
        fun newInstance(access_token: String?, refresh_token: String?, expire: Long?) =
            NavigationFragment().apply {
                arguments = Bundle().apply {
                    putString(ACCESS_TOKEN, access_token)
                    putString(REFRESH_TOKEN, refresh_token)
                    if (expire != null) {
                        putLong(EXPIRES, expire)
                    }
                }
            }
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),viewModelFactory)[DroidFleetViewModel::class.java]
        arguments?.let {
            accessToken = it.getString(ACCESS_TOKEN).toString()
            refreshToken = it.getString(REFRESH_TOKEN).toString()
            expire = it.getLong(EXPIRES).toInt()
        }
        viewModel.isUserLoggedIn = true
        viewModel.loadAllSettings(AuthorizationProperties(accessToken,expire,refreshToken))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_navigation, container, false)
        val navView: BottomNavigationView = root.findViewById(R.id.nav_view)
        val navController =
            (childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
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
        navView.setupWithNavController(navController)

        viewModel.selectedDevice.observe(viewLifecycleOwner) {
            when(it){
                is SelectedDevice.Device ->  navView.selectedItemId = R.id.mapsFragment
                is SelectedDevice.Reset -> Log.d("SELECTEDLIVEDATA", "NavigationFragment ${it.message}")
            }


        }
        return root
    }

}