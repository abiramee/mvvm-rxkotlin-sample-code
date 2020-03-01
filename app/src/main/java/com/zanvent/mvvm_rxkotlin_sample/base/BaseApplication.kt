package com.zanvent.mvvm_rxkotlin_sample.base

import android.app.Activity
import com.zanvent.mvvm_rxkotlin_sample.di.components.ApplicationComponent
import com.zanvent.mvvm_rxkotlin_sample.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       val component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this)
        return component;
    }
}