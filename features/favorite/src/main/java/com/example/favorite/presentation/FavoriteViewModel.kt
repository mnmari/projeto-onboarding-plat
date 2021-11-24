package com.example.favorite.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.favorite.domain.GetFavoriteListUseCase
import com.example.favorite.domain.entity.FavoriteStore
import com.example.favorite.presentation.utils.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(private val getFavoriteListUseCase: GetFavoriteListUseCase) : DisposableViewModel() {

    private val _storeLiveData = MutableLiveData<List<FavoriteStore>>()
    val storeLiveData : LiveData<List<FavoriteStore>> = _storeLiveData

    private val _errorStateLiveData = MutableLiveData<Throwable>()
    val errorStateLiveData : LiveData<Throwable> = _errorStateLiveData

    fun getFavoriteList() {
        getFavoriteListUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _storeLiveData.value = it
                },
                {
                    Log.e("ErroReq", "erro: " + it.cause)
                    _errorStateLiveData.value = it
                }
            ).handleDisposable()
    }
}