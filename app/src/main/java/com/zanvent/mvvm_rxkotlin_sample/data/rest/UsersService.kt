package com.zanvent.mvvm_rxkotlin_sample.data.rest

import com.zanvent.mvvm_rxkotlin_sample.base.models.Users
import com.zanvent.mvvm_rxkotlin_sample.data.models.DetailedUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersService {
    @GET("search/users?q=location:bangladesh")
    fun getUsersData(): Single<Users>

    @GET("users/{username}")
    fun getDetailedUserData(@Path("username") username: String) : Single<DetailedUser>

    @GET("https://api.github.com/search/users")
    fun getSearchUsersData(@Query("q") search: String) : Single<Users>
}