package com.zanvent.mvvm_rxkotlin_sample.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zanvent.mvvm_rxkotlin_sample.base.models.Users
import com.zanvent.mvvm_rxkotlin_sample.data.models.DetailedUser
import com.zanvent.mvvm_rxkotlin_sample.data.rest.UsersService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.completable.CompletableDisposeOn
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.reflect.full.valueParameters

class MainActivityViewModel @Inject constructor(
    private val usersService: UsersService,
    application: Application
) :
    AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private var _usersData = MutableLiveData<List<DetailedUser>>()
    val usersData: LiveData<List<DetailedUser>>
        get() = _usersData

    private val _loadingState = MutableLiveData<Int>()
    val loadingState: LiveData<Int>
        get() = _loadingState

    private val _errorState = MutableLiveData<Boolean>()
    val errorState: LiveData<Boolean>
        get() = _errorState

    private val _initialUsers = MutableLiveData<List<DetailedUser>>(emptyList())
    private val _searchUsers = MutableLiveData<List<DetailedUser>>(emptyList())
    private var isSearchEnabled = false;
    private var searchKeywordsPublishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        _initialUsers.value = ArrayList()
        getUsersData()
        observeSearchKeyword()
    }

    @SuppressLint("CheckResult")
    private fun getUsersData() {
        _loadingState.value = View.VISIBLE
        _errorState.value = false
        compositeDisposable.add(usersService.getUsersData()
            .subscribeOn(Schedulers.io())
            .toObservable()
            .flatMap {
                Observable.fromIterable(it.items)
            }.flatMap {
                usersService.getDetailedUserData(it.login).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Log.e("hello", it.toString())
                    _initialUsers.value = _initialUsers.value!!.plus(it)
                    if (!isSearchEnabled) {
                        _usersData.value = _initialUsers.value
                    }
                },
                onError = {
                    _errorState.value = true
                    Log.e("hello", "error", it)
                },
                onComplete = {
                    _loadingState.value = View.GONE
                }
            )
        )
    }

    @SuppressLint("CheckResult")
    private fun observeSearchKeyword() {
        compositeDisposable.add(searchKeywordsPublishSubject
            .observeOn(Schedulers.io())
            .debounce(1, TimeUnit.SECONDS)
            .filter {
                isSearchEnabled = true;
                it.length > 3
            }
            .switchMap {
                Log.e("hello", it)
                usersService.getSearchUsersData(it).toObservable()
            }.flatMap {
                _searchUsers.postValue(emptyList())
                Log.e("Hello", it.items.size.toString())
                Observable.fromIterable(it.items)
            }.flatMap flatmap@{
                return@flatmap usersService.getDetailedUserData(it.login).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _searchUsers.value = _searchUsers.value!!.plus(it)
                    if (isSearchEnabled) {
                        _usersData.value = _searchUsers.value
                    }
                },
                onError = {
                    _errorState.value = true
                    Log.e("hello", "error", it)
                },
                onComplete = {

                }
            )
        )
    }

    fun onChangeSearch(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.e("hello", s.toString())
        if (s.length <= 3) {
            if (!isSearchEnabled) {
                _usersData.value = _initialUsers.value
            } else {
                _loadingState.value = View.INVISIBLE
            }
            isSearchEnabled = false;
        }
        searchKeywordsPublishSubject.onNext(s.toString());
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
