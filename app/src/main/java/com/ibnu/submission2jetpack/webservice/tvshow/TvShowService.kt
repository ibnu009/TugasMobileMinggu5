package com.ibnu.submission2jetpack.webservice.tvshow

import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.webservice.jsonobject.CastsResult
import com.ibnu.submission2jetpack.webservice.jsonobject.ResultTvShow
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET("/3/tv/popular")
    fun getPopularTvShow(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<ResultTvShow>

    @GET("/3/tv/top_rated")
    fun getTopRatedTvShow(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<ResultTvShow>

    @GET("/3/tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") tvId: String,
        @Query("language") language: String
    ): Observable<TvShow>

    @GET("/3/tv/{tv_id}/credits")
    fun getTvShowCast(
        @Path("tv_id") tvId: String,
        @Query("language") language: String
    ): Observable<CastsResult>

    @GET("/3/tv/{tv_id}/similar")
    fun getSimilarTvShow(
        @Path("tv_id") tvId: String,
        @Query("language") language: String
    ): Observable<ResultTvShow>

    @GET("/3/tv/{tv_id}/recommendations")
    fun getRecommendedTvShow(
        @Path("tv_id") tvId: String,
        @Query("language") language: String
    ): Observable<ResultTvShow>

}