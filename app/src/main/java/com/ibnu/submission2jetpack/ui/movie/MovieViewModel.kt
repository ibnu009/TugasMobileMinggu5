package com.ibnu.submission2jetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.paging.ResponsePagedMovie
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.repo.MovieRepository
import com.ibnu.submission2jetpack.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val movieFromVM = MutableLiveData<PagedList<Movie>>()

    fun getMovies(type: String): LiveData<PagedList<Movie>> {
        Timber.d("Check")
        return movieRepository.getMoviesData(type, viewModelScope, object :
            ResponsePagedMovie {
            override fun onSuccess(list: PagedList<Movie>) {
                Timber.d("Size of MovieviewModelData is ${list.size}")
                movieFromVM.postValue(list)
            }
            override fun onFailed() {
                Timber.e("Error")
            }
        })
    }

    fun listIsEmpty(type: String):Boolean {
        return getMovies(type).value?.isEmpty() ?: true
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }
}