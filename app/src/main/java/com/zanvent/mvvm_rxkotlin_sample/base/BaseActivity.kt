package com.zanvent.mvvm_rxkotlin_sample.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import butterknife.ButterKnife
import dagger.android.DaggerActivity

abstract class BaseActivity : DaggerActivity() {
    @LayoutRes
    protected abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(layoutRes())
        ButterKnife.bind(this)
    }
}