package com.ibnu.submission2jetpack.di

import android.content.Context
import com.ibnu.submission2jetpack.data.repo.FavoriteRepository
import com.ibnu.submission2jetpack.data.repo.MovieRepository
import com.ibnu.submission2jetpack.data.repo.PeopleRepository
import com.ibnu.submission2jetpack.data.repo.TvRepository
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideMovieService() = RetrofitApp.getMovieApiService()

    @Singleton
    @Provides
    fun provideTvService() = RetrofitApp.getTVShowApiService()

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Singleton
    @Provides
    fun provideMovieRepository(@ApplicationContext context: Context) =
        MovieRepository(context, provideMovieService())

    @Singleton
    @Provides
    fun provideFavoriteRepository(@ApplicationContext context: Context) = FavoriteRepository(context)

    @Singleton
    @Provides
    fun providePeopleRepository() = PeopleRepository()

    @Singleton
    @Provides
    fun provideTvShowRepository(@ApplicationContext context: Context) = TvRepository(context, provideTvService())


}