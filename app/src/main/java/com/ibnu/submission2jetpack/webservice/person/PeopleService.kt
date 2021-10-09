package com.ibnu.submission2jetpack.webservice.person

import com.ibnu.submission2jetpack.webservice.jsonobject.ResultPeople
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {

    @GET("/3/person/popular")
    fun getPeopleData(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<ResultPeople>

}