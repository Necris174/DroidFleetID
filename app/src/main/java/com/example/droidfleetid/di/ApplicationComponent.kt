package com.example.droidfleetid.di

import android.app.Application
import com.example.droidfleetid.presentation.MainActivity
import com.example.droidfleetid.presentation.fragments.LoginFragment
import com.example.droidfleetid.presentation.fragments.NavigationFragment
import com.example.droidfleetid.presentation.fragments.SettingsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject (fragment: LoginFragment)

    fun inject (navigationFragment: NavigationFragment)

    fun inject (mainActivity: MainActivity)

    fun inject (settingsFragment: SettingsFragment)

        @Component.Factory
        interface Factory {

            fun create (
                @BindsInstance application: Application
            ): ApplicationComponent
            }

}