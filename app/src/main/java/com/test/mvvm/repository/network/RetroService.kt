package com.test.mvvm.repository.network

import com.test.mvvm.viewmodel.BookListItemModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

@GET("volumes")
fun getBookListFromApi(@Query("q") query: String): Observable<BookListItemModel>//use in rx java other vise use call liSt
}