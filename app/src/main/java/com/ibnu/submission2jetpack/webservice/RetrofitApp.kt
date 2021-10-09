package com.ibnu.submission2jetpack.webservice

import com.ibnu.submission2jetpack.BuildConfig
import com.ibnu.submission2jetpack.webservice.movie.MovieService
import com.ibnu.submission2jetpack.webservice.person.PeopleService
import com.ibnu.submission2jetpack.webservice.tvshow.TvShowService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApp {

    companion object {
        const val FIRST_PAGE = 1
        const val ITEM_PER_PAGE = 18

        private val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        fun getMovieApiService(): MovieService {
            val client = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()

            return retrofit.create(MovieService::class.java)
        }

        fun getPeopleApiService(): PeopleService {
            val client = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

            return retrofit.create(PeopleService::class.java)
        }

        fun getTVShowApiService(): TvShowService {
            val client = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

            return retrofit.create(TvShowService::class.java)
        }

    }

}