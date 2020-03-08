package com.zanvent.mvvm_rxkotlin_sample.di.components

import android.app.Application
import com.zanvent.mvvm_rxkotlin_sample.base.BaseApplication
import com.zanvent.mvvm_rxkotlin_sample.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ActivityBindingModule::class, ContextModule::class, ApplicationModule::class, ViewModelModule::class])

interface ApplicationComponent : AndroidInjector<DaggerApplication>{
    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : ApplicationComponent
    }

}