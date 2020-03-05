package com.zanvent.mvvm_rxkotlin_sample.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zanvent.mvvm_rxkotlin_sample.R
import com.zanvent.mvvm_rxkotlin_sample.base.BaseApplication
import com.zanvent.mvvm_rxkotlin_sample.base.models.Users
import com.zanvent.mvvm_rxkotlin_sample.databinding.ActivityMainBinding
import com.zanvent.mvvm_rxkotlin_sample.ui.main.adapters.UsersAdapter
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
    lateinit var viewModelFactory: ViewModelProviderFactory;
    @Inject
    lateinit var retrofit: Retrofit
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = adapter
        observeUsers()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        adapter = UsersAdapter()
    }

    private fun observeUsers() {
        viewModel.usersData.observe(this, androidx.lifecycle.Observer {
            if (viewModel.searchUsers.value.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        })

        viewModel.searchUsers.observe(this, androidx.lifecycle.Observer {
            if (viewModel.isSearchEnabled) {
                adapter.submitList(it)
            } else {
                adapter.submitList(viewModel.usersData.value)
            }
        })
    }
}
