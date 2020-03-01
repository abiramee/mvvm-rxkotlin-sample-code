package com.zanvent.mvvm_rxkotlin_sample.data.rest;

import com.zanvent.mvvm_rxkotlin_sample.base.models.Users;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {
    @GET("https://api.github.com/search/users?q=location:bangladesh")
    Single<Users> getUsers();
}
