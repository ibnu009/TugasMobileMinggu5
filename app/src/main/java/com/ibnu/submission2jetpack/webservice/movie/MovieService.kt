package com.ibnu.submission2jetpack.webservice.movie

import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.webservice.jsonobject.CastsResult
import com.ibnu.submission2jetpack.webservice.jsonobject.Result
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("language") language: String
    ): Movie

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: String,
        @Query("language") language: String
    ): CastsResult

    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: String,
        @Query("language") language: String
    ): Result

    @GET("/3/movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: String,
        @Query("language") language: String
    ): Result


}