package com.zanvent.mvvm_rxkotlin_sample.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zanvent.mvvm_rxkotlin_sample.di.util.ViewModelKey
import com.zanvent.mvvm_rxkotlin_sample.ui.main.MainActivityViewModel
import com.zanvent.mvvm_rxkotlin_sample.util.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}