package com.ibnu.submission2jetpack.ui.tvshow.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ibnu.submission2jetpack.BuildConfig
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.Season
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.universaladapter.CastAdapter
import com.ibnu.submission2jetpack.universaladapter.RecommendedTvShowAdapter
import com.ibnu.submission2jetpack.universaladapter.SimilarTvShowAdapter
import com.ibnu.submission2jetpack.utils.GenreConverter
import com.ibnu.submission2jetpack.utils.TypeUtils.TV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import kotlinx.android.synthetic.main.activity_tv_show_detail.tv_recommended
import kotlinx.android.synthetic.main.activity_tv_show_detail.tv_similar

@Suppress("RedundantSamConstructor")
@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {
    private var statusFavorite = 0

    companion object{
        const val EXTRA_ID = "0"
    }

    private val tvDetailViewModel : TvShowDetailViewModel by viewModels()

//    private val tvDetailViewModel by lazy {
//        val factory = ViewModelFactory.getInstance(this.applicationContext)
//        ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]
//    }

    private val onClickedTvShow = object : OnClickedAction{
        override fun onClicked(id: Int) {
            val intent = Intent(this@TvShowDetailActivity, TvShowDetailActivity::class.java).apply {
                putExtra(EXTRA_ID, id)
            }
            startActivity(intent)
        }
    }

    private lateinit var castAdapter: CastAdapter
    private lateinit var similarTvShowAdapter: SimilarTvShowAdapter
    private lateinit var recommendedTvShowAdapter: RecommendedTvShowAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        supportActionBar?.hide()

        castAdapter = CastAdapter()
        similarTvShowAdapter = SimilarTvShowAdapter(onClickedTvShow)
        recommendedTvShowAdapter = RecommendedTvShowAdapter(onClickedTvShow)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        tvDetailViewModel.checkIsFavorite(id).observe(this, Observer {
            if (id == it) {
                statusFavorite = 1
                isFavorite(true)
            }
        })

        tv_show_progressbar.visibility = View.VISIBLE

        tvDetailViewModel.getTvShowDetail(id).observe(this, Observer {tvShow ->
            Glide.with(this).load("${BuildConfig.BACKDROP_PATH}${tvShow.tvShowPosterPath}")
                .placeholder(R.drawable.ic_television).into(img_detail_tv_show_poster)
            Glide.with(this).load("${BuildConfig.BACKDROP_PATH}${tvShow.tvShowBackdropPath}")
                .placeholder(R.drawable.ic_television).into(img_detail_tv_show)
            tv_title_detail_tv_show.text = tvShow.tvShowName
            tv_rating_detail_tv_show.text = tvShow.tvShowAverageRating.toString()
            tv_description_detail_tv_show.text = tvShow.tvShowDescription
            tv_genre_detail_tv_show.text = GenreConverter().displayGenreOnDetailTv(tvShow, tvShow.tvShowDetailCategory)
            tv_show_progressbar.visibility = View.GONE

            btn_tv_fav.setOnClickListener {
                when (statusFavorite) {
                    1 -> {
                        statusFavorite = 0
                        tvDetailViewModel.deleteMovieFromFavorite(id)
                        isFavorite(false)
                        Toast.makeText(this, "TV Show removed from favorite", Toast.LENGTH_SHORT).show()
                    }
                    0 -> {
                        statusFavorite = 1
                        val favoriteMovie = FavoriteItemEntity(
                            id,
                            tvShow.tvShowPosterPath,
                            tvShow.tvShowName,
                            TV)
                        tvDetailViewModel.insertToFavorite(favoriteMovie)
                        isFavorite(true)
                        Toast.makeText(this, "TV Show added to favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            displaySeason(tvShow.tvShowSeason)
        })

        tvDetailViewModel.getTvShowCast(id).observe(this, Observer {
            initCastsRecycleView(it)
        })

        tvDetailViewModel.getSimilarTvShow(id).observe(this, Observer {
            if (it.isNotEmpty()) {
                tv_similar.visibility = View.VISIBLE
                initSimilarTvShowRv(it)
            }
        })

        tvDetailViewModel.getRecommendedTvShow(id).observe(this, Observer {
            if (it.isNotEmpty()) {
                tv_recommended.visibility = View.VISIBLE
                initRecommendedTvShowRv(it)
            }
        })
    }

    private fun initCastsRecycleView(listCast: List<Cast>) {
        castAdapter.updateCastData(cast = listCast)
        with(rv_cast_tv_show) {
            adapter = castAdapter
            layoutManager =
                LinearLayoutManager(this@TvShowDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initSimilarTvShowRv(listTvShow: List<TvShow>) {
        similarTvShowAdapter.updateData(listTvShow)
        with(rv_similar_tv_show){
            adapter = similarTvShowAdapter
            layoutManager = LinearLayoutManager(this@TvShowDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initRecommendedTvShowRv(listTvShow: List<TvShow>) {
        recommendedTvShowAdapter.updateData(listTvShow)
        with(rv_recommended_tv_show){
            adapter = recommendedTvShowAdapter
            layoutManager = LinearLayoutManager(this@TvShowDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun displaySeason(seasons: List<Season>) {
        for (season in seasons) {
            Glide.with(this@TvShowDetailActivity).load("${BuildConfig.POSTER_PATH}${season.seasonPoster}")
                .placeholder(R.drawable.ic_television)
                .into(img_cover_season)
            tv_current_season.text = season.seasonName
            tv_season_total_episodes.text = getString(R.string.season_date_and_episodes, season.seasonDate?:"--", season.seasonEpisodeCount.toString())
            tv_season_desc.text = stringCheckerForOverview(season.seasonDesc)
        }
    }

    private fun stringCheckerForOverview(string: String): String {
        return if (string.isEmpty()) {
            "We don't have an overview"
        } else{
            string
        }
    }

    private fun isFavorite(status: Boolean) {
        if (status) {
            btn_tv_fav.setBackgroundResource(R.drawable.ic_hearted)
        } else {
            btn_tv_fav.setBackgroundResource(R.drawable.ic_heart)
        }
    }
}