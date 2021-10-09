package com.ibnu.submission2jetpack.ui.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.ui.movie.adapter.MoviePagedListAdapter
import com.ibnu.submission2jetpack.ui.movie.detail.MovieDetailActivity
import com.ibnu.submission2jetpack.ui.movie.detail.MovieDetailActivity.Companion.EXTRA_ID
import com.ibnu.submission2jetpack.utils.NetworkState
import com.ibnu.submission2jetpack.utils.ResponseEnum
import com.ibnu.submission2jetpack.utils.TypeUtils.TOP_RATED
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_fragment.*
import java.util.*

@Suppress("RedundantSamConstructor")
@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var moviePagedListAdapter: MoviePagedListAdapter

    private val viewModel: MovieViewModel by viewModels()


    private val onMovieClicked = object : OnClickedAction {
        override fun onClicked(id: Int) {
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_ID, id)
            }
            startActivity(intent)
        }
    }

    companion object {
        val responseLoading = ResponseEnum.LOADING.status
        val responseLoaded = ResponseEnum.LOADED.status
        val responseEmpty = ResponseEnum.EMPTY.status
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.movies)

        responseWatcher(responseLoading)
        moviePagedListAdapter = MoviePagedListAdapter(onClickedAction = onMovieClicked)

        loadDataMovie(TOP_RATED)

        with(rv_movie) {
            adapter = moviePagedListAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }

        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.top_rated))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.popular))
        tabMovieOnClick(tab_layout_movie)
    }

    private fun tabMovieOnClick(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                responseWatcher(responseLoading)
                loadDataMovie(tab?.text.toString().toLowerCase(Locale.getDefault()))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun loadDataMovie(type: String) {
        viewModel?.getMovies(type)?.observe(viewLifecycleOwner, Observer {
            moviePagedListAdapter.submitList(it)
            responseWatcher(responseLoaded)
        })
        viewModel?.networkState?.observe(viewLifecycleOwner, Observer {
            if (viewModel!!.listIsEmpty(type) && it == NetworkState.LOADING) responseWatcher(
                responseLoading
            ) else responseWatcher(
                responseLoaded
            )
            if (viewModel!!.listIsEmpty(type) && it == NetworkState.ERROR) responseWatcher(
                responseEmpty
            ) else responseWatcher(
                responseLoaded
            )
        })

    }

    private fun responseWatcher(status: String) {
        when (status) {
            responseEmpty -> {
                tv_null_response.visibility = View.VISIBLE
                img_null_response.visibility = View.VISIBLE
                progress_bar_movie.visibility = View.GONE
            }
            responseLoading -> {
                tv_null_response.visibility = View.GONE
                img_null_response.visibility = View.GONE
                progress_bar_movie.visibility = View.VISIBLE
            }
            responseLoaded -> {
                tv_null_response.visibility = View.GONE
                img_null_response.visibility = View.GONE
                progress_bar_movie.visibility = View.GONE
            }
        }
    }
}