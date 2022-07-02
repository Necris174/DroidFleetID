package com.example.droidfleetid.di

import androidx.lifecycle.ViewModel
import com.example.droidfleetid.presentation.DroidFleetViewModel
import com.example.droidfleetid.presentation.fragments.LoginFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    fun bindLoginFragmentViewModel (viewModel: LoginFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DroidFleetViewModel::class)
    fun bindDroidFleetViewModel (viewModel: DroidFleetViewModel): ViewModel

}