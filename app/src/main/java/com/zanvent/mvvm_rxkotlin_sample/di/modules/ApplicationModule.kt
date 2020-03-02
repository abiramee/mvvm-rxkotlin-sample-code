package com.zanvent.mvvm_rxkotlin_sample.di.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zanvent.mvvm_rxkotlin_sample.data.rest.UsersService
import dagger.Module
import dagger.Provides
import io.reactivex.plugins.RxJavaPlugins
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module()
class ApplicationModule {
    @Singleton
    @Provides
    fun provideRetrofitInstance() : Retrofit {
        return Retrofit.Builder().baseUrl("https://api.github.com/search/users/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit) : UsersService {
        return retrofit.create(UsersService::class.java)
    }
}