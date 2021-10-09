package com.ibnu.submission2jetpack.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedActionFavorite
import com.ibnu.submission2jetpack.ui.favorite.adapter.FavoritePagedListAdapter
import com.ibnu.submission2jetpack.ui.movie.detail.MovieDetailActivity
import com.ibnu.submission2jetpack.ui.tvshow.detail.TvShowDetailActivity
import com.ibnu.submission2jetpack.utils.TypeUtils.MOVIE
import com.ibnu.submission2jetpack.utils.TypeUtils.TV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_fragment.*


@Suppress("RedundantSamConstructor")
@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var pagedAdapter: FavoritePagedListAdapter

    private val viewModel: FavoriteViewModel by viewModels()

    private val onMovieClicked = object : OnClickedActionFavorite {
        override fun onClicked(id: Int, type: String) {
            val intent: Intent = if (type == MOVIE) {
                Intent(context, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailActivity.EXTRA_ID, id)
                }
            } else {
                Intent(context, TvShowDetailActivity::class.java).apply {
                    putExtra(TvShowDetailActivity.EXTRA_ID, id)
                }
            }
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.favorite)

        pagedAdapter = FavoritePagedListAdapter(onMovieClicked)

        with(rv_favorite) {
            adapter = pagedAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
        loadData(TV)
        tv_sorted_by.text = getString(R.string.sorted_by, TV)
        btn_sort.setOnClickListener {
            showSelectionDialog()
        }
    }

    private fun loadData(type: String) {
        viewModel?.getFavoriteItems(type)?.observe(viewLifecycleOwner, Observer {
            pagedAdapter.submitList(it)
        })
    }

    private fun showSelectionDialog() {
        val alertDialog = this.context?.let { AlertDialog.Builder(it) }
        alertDialog?.setTitle("Choose Type Sorting")
        val items = arrayOf("Movie", "Tv Show")
        val checkedItem = 0

        alertDialog?.setSingleChoiceItems(
            items,
            checkedItem
        ) { _, p1 ->
            when (p1) {
                0 -> {
                    loadData(MOVIE)
                    tv_sorted_by.text = getString(R.string.sorted_by, MOVIE)
                }
                1 -> {
                    loadData(TV)
                    tv_sorted_by.text = getString(R.string.sorted_by, TV)
                }
            }
        }
        alertDialog?.setPositiveButton(
            "Ok"
        ) { p0, _ -> p0?.dismiss() }
        val alert = alertDialog?.create() as AlertDialog
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

}