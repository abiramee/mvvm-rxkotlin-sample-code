package com.zanvent.mvvm_rxkotlin_sample.di.modules

import androidx.annotation.MainThread
import com.zanvent.mvvm_rxkotlin_sample.ui.main.MainActivity
import com.zanvent.mvvm_rxkotlin_sample.ui.main.MainActivityViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.jetbrains.annotations.Contract

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector()
    abstract fun bindMainActivity() : MainActivity
}
