package com.zanvent.mvvm_rxkotlin_sample.di.modules

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.zanvent.mvvm_rxkotlin_sample.R
import com.zanvent.mvvm_rxkotlin_sample.data.rest.UsersService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module()
class ApplicationModule {
    @Singleton
    @Provides
    fun provideRetrofitInstance(application: Application): Retrofit {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("Authorization",
                        "token ${application.applicationContext.getString(R.string.github_authentication_token)}").build()
            chain.proceed(request)
        }
        return Retrofit.Builder().baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UsersService {
        return retrofit.create(UsersService::class.java)
    }
}