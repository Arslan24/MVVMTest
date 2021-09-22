package com.test.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mvvm.repository.network.RetroInstance
import com.test.mvvm.repository.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {

    var bookList: MutableLiveData<BookListItemModel> = MutableLiveData()

    fun getBookListObserver(): MutableLiveData<BookListItemModel> //update recyclerview in activity
    {
        return bookList
    }

    fun makeApiCall(query: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)

        return retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx(): Observer<BookListItemModel> {
        return object : Observer<BookListItemModel> {

            override fun onSubscribe(d: Disposable) {
                //show progress
            }

            override fun onNext(t: BookListItemModel) {
                bookList.postValue(t)
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
            }

            override fun onComplete() {
                //hide progress
            }

        }
    }
}