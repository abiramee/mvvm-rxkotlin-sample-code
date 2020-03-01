package com.zanvent.mvvm_rxkotlin_sample.base.models

data class Users(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)