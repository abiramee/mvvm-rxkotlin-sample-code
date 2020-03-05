package com.zanvent.mvvm_rxkotlin_sample.ui.main

import android.annotation.SuppressLint
import android.app.Application
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

    private var _usersData = MutableLiveData<List<DetailedUser>>(emptyList())
    val usersData: LiveData<List<DetailedUser>>
        get() = _usersData

    private val _loadingState = MutableLiveData<Int>()
    val loadingState: LiveData<Int>
        get() = _loadingState

    private val _errorState = MutableLiveData<Boolean>()
    val errorState: LiveData<Boolean>
        get() = _errorState

    private val _searchUsers = MutableLiveData<List<DetailedUser>>(emptyList())
    val searchUsers: LiveData<List<DetailedUser>>
        get() = _searchUsers;
    private var searchKeywordsPublishSubject: PublishSubject<String> = PublishSubject.create()
    var isSearchEnabled = false;
    private var searchItemCount  = 0

    init {
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
                    _usersData.value = _usersData.value!!.plus(it)
                },
                onError = {
                    _errorState.value = true
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
                it.length > 3
            }
            .switchMap {
                isSearchEnabled = true;
                _loadingState.postValue(View.VISIBLE)
                usersService.getSearchUsersData(it).toObservable()
            }.flatMap {
                searchItemCount = it.items.size
                _searchUsers.postValue(emptyList())
                Observable.fromIterable(it.items)
            }.flatMap {
                usersService.getDetailedUserData(it.login).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _searchUsers.value = _searchUsers.value!!.plus(it)
                    if ((_searchUsers.value as List<DetailedUser>).size == searchItemCount) {
                        _loadingState.value = View.GONE
                    }
                },
                onError = {
                    _errorState.value = true
                },
                onComplete = {

                }
            )
        )
    }

    fun onChangeSearch(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.length <= 2) {
            isSearchEnabled = false;
            _searchUsers.postValue(emptyList())
            _loadingState.postValue(View.GONE)
        }
        searchKeywordsPublishSubject.onNext(s.toString());
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
