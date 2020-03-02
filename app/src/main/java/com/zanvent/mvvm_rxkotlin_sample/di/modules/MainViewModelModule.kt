package com.zanvent.mvvm_rxkotlin_sample.di.modules

import android.view.View
import androidx.lifecycle.ViewModel
import com.zanvent.mvvm_rxkotlin_sample.di.util.ViewModelKey
import com.zanvent.mvvm_rxkotlin_sample.ui.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMyViewModel(myViewModel: MainActivityViewModel): ViewModel
}