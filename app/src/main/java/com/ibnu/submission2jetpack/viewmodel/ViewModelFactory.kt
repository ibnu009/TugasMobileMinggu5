package com.ibnu.submission2jetpack.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibnu.submission2jetpack.data.repo.FavoriteRepository
import com.ibnu.submission2jetpack.data.repo.MovieRepository
import com.ibnu.submission2jetpack.data.repo.PeopleRepository
import com.ibnu.submission2jetpack.data.repo.TvRepository
import com.ibnu.submission2jetpack.ui.favorite.FavoriteViewModel
import com.ibnu.submission2jetpack.ui.movie.MovieViewModel
import com.ibnu.submission2jetpack.ui.movie.detail.MovieDetailViewModel
import com.ibnu.submission2jetpack.ui.people.PeopleViewModel
import com.ibnu.submission2jetpack.ui.tvshow.TvShowViewModel
import com.ibnu.submission2jetpack.ui.tvshow.detail.TvShowDetailViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

class ViewModelFactory(
    private val mMovieRepository: MovieRepository,
    private val mTvRepository: TvRepository,
    private val mPeopleRepository: PeopleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(mMovieRepository,favoriteRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(mTvRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(TvShowDetailViewModel::class.java) -> {
                TvShowDetailViewModel(mTvRepository, favoriteRepository,compositeDisposable) as T
            }
            modelClass.isAssignableFrom(PeopleViewModel::class.java) -> {
                PeopleViewModel(mPeopleRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(favoriteRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}