package com.zanvent.mvvm_rxkotlin_sample.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zanvent.mvvm_rxkotlin_sample.R
import com.zanvent.mvvm_rxkotlin_sample.base.BaseApplication
import com.zanvent.mvvm_rxkotlin_sample.di.components.ApplicationComponent
import com.zanvent.mvvm_rxkotlin_sample.di.components.DaggerApplicationComponent
import com.zanvent.mvvm_rxkotlin_sample.di.modules.ApplicationModule
import com.zanvent.mvvm_rxkotlin_sample.util.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewmodelFactory: ViewModelProviderFactory;

    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var viewModel: ViewModel

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.interval(4, TimeUnit.SECONDS).subscribe {
            retrofit?.let {

        }
    }
}
