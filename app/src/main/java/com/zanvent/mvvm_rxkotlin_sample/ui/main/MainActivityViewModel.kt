package com.zanvent.mvvm_rxkotlin_sample.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.zanvent.mvvm_rxkotlin_sample.data.rest.UsersService
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application){

    init {
        Log.e("hello", "dfdsf");
    }
}