package com.ibnu.submission2jetpack.ui.movie.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ibnu.submission2jetpack.BuildConfig
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.universaladapter.CastAdapter
import com.ibnu.submission2jetpack.universaladapter.RecommendedMovieAdapter
import com.ibnu.submission2jetpack.universaladapter.SimilarMovieAdapter
import com.ibnu.submission2jetpack.utils.GenreConverter
import com.ibnu.submission2jetpack.utils.TypeUtils.MOVIE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@Suppress("RedundantSamConstructor")
@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private var statusFavorite = 0

    companion object {
        const val EXTRA_ID = "EXTRA ID"
    }

    private val detailMovieViewModel: MovieDetailViewModel by viewModels()

    private val onMovieClicked = object : OnClickedAction {
        override fun onClicked(id: Int) {
            val intent = Intent(this@MovieDetailActivity, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_ID, id)
            }
            startActivity(intent)
        }
    }

    private lateinit var creditsAdapter: CastAdapter
    private lateinit var similarMovieAdapter: SimilarMovieAdapter
    private lateinit var recommendedMovieAdapter: RecommendedMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.hide()


        creditsAdapter = CastAdapter()
        similarMovieAdapter = SimilarMovieAdapter(onMovieClicked)
        recommendedMovieAdapter = RecommendedMovieAdapter(onMovieClicked)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        Timber.d("Check Id from movie is ${detailMovieViewModel.checkIsFavorite(id)}")

        detailMovieViewModel.checkIsFavorite(id).observe(this, Observer {
            if (id == it) {
                statusFavorite = 1
                isFavorite(true)
            }
        })

        movie_progressbar.visibility = View.VISIBLE

        detailMovieViewModel.getDetailMovie(id).observe(this, Observer { movie ->
                Glide.with(this)
                    .load("${BuildConfig.BACKDROP_PATH}${movie.moviePosterPath}")
                    .placeholder(R.drawable.ic_video_player).into(img_detail_movie_poster)
                Glide.with(this)
                    .load("${BuildConfig.BACKDROP_PATH}${movie.movieBackdropPath}")
                    .placeholder(R.drawable.ic_video_player).into(img_detail_movie)
                tv_title_detail_movie.text = movie.movieName
                tv_rating_detail_movie.text = movie.movieAverageRating.toString()
                tv_description_detail_movie.text = movie.movieDescription

//            To get Genre from Custom Genre Converter
                val genre =
                    GenreConverter().displayGenreOnDetailMovie(movie, movie.movieDetailCategory)
                tv_genre_detail_movie.text = genre

                btn_movie_fav.setOnClickListener {
                    when (statusFavorite) {
                        1 -> {
                            statusFavorite = 0
                            detailMovieViewModel.deleteMovieFromFavorite(id)
                            isFavorite(false)
                            Toast.makeText(
                                this,
                                "Movie removed from favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        0 -> {
                            statusFavorite = 1
                            val favoriteMovie = FavoriteItemEntity(
                                id,
                                movie.moviePosterPath,
                                movie.movieName,
                                MOVIE
                            )
                            Toast.makeText(
                                this,
                                "Movie added to favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                            detailMovieViewModel.insertToFavorite(favoriteMovie)
                            isFavorite(true)
                        }
                    }
                }
                movie_progressbar.visibility = View.GONE
            })

        detailMovieViewModel.getCasts(id).observe(this, Observer { cast ->
            tv_cast.visibility = View.VISIBLE
            if (cast.isEmpty()) {
                rv_cast_movie.visibility = View.GONE
            } else {
                tv_cast_if_null.visibility = View.GONE
                initCastsRecycleView(cast)
            }
        })

        detailMovieViewModel.getSimilarMovies(id).observe(this, Observer { similarMovies ->
            if (similarMovies.isNotEmpty()) {
                tv_similar.visibility = View.VISIBLE
                initSimilarMovieRecycleView(similarMovies)
            }
        })

        detailMovieViewModel.getRecommendedMovies(id).observe(this, Observer { recommendedMovies ->
            if (recommendedMovies.isNotEmpty()) {
                tv_recommended.visibility = View.VISIBLE
                initRecommendedMovieRecycleView(recommendedMovies)
            }
        })
    }

    private fun initCastsRecycleView(listCast: List<Cast>) {
        creditsAdapter.updateCastData(cast = listCast)
        with(rv_cast_movie) {
            adapter = creditsAdapter
            layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun initSimilarMovieRecycleView(listMovie: List<Movie>) {
        similarMovieAdapter.updateData(listMovie)
        with(rv_similar_movie) {
            adapter = similarMovieAdapter
            layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initRecommendedMovieRecycleView(list: List<Movie>) {
        recommendedMovieAdapter.updateData(list)
        with(rv_recommended_movie) {
            adapter = recommendedMovieAdapter
            layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun isFavorite(status: Boolean) {
        if (status) {
            btn_movie_fav.setBackgroundResource(R.drawable.ic_hearted)
        } else {
            btn_movie_fav.setBackgroundResource(R.drawable.ic_heart)
        }
    }

}