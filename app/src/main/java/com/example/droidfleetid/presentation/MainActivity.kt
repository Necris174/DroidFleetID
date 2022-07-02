package com.example.droidfleetid.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.droidfleetid.DFApp
import com.example.droidfleetid.R
import com.example.droidfleetid.presentation.fragments.LoginFragment
import com.example.droidfleetid.presentation.fragments.NavigationFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as DFApp).component
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accessToken = sharedPreferences.getString("accessToken", "")
        val expires = sharedPreferences.getLong("expires", 0)
        val refreshToken = sharedPreferences.getString("refreshToken", "")
        if (accessToken != null&& expires-System.currentTimeMillis()>0) {

         supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out)
                .replace(
                    R.id.droid_fleet_container,
                    NavigationFragment.newInstance(
                       accessToken,
                       refreshToken,
                        expires
                    )
                ).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.droid_fleet_container, LoginFragment()).commit()
        }

    }
}


