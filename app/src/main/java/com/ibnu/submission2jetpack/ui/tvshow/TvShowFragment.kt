package com.ibnu.submission2jetpack.ui.tvshow

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
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.ui.tvshow.adapter.TvShowPagedListAdapter
import com.ibnu.submission2jetpack.ui.tvshow.detail.TvShowDetailActivity
import com.ibnu.submission2jetpack.ui.tvshow.detail.TvShowDetailActivity.Companion.EXTRA_ID
import com.ibnu.submission2jetpack.utils.ResponseEnum
import com.ibnu.submission2jetpack.utils.TypeUtils.TOP_RATED
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.tv_show_fragment.*
import timber.log.Timber
import java.util.*

@Suppress("RedundantSamConstructor")
@AndroidEntryPoint
class TvShowFragment : Fragment() {

    companion object{
        val responseLoading = ResponseEnum.LOADING.status
        val responseLoaded = ResponseEnum.LOADED.status
        val responseEmpty = ResponseEnum.EMPTY.status
    }

    private lateinit var tvPagedListAdapter: TvShowPagedListAdapter

    private val viewModel : TvShowViewModel by viewModels()
//    private val viewModel by lazy {
//        val factory = ViewModelFactory.getInstance(this.requireContext())
//        ViewModelProvider(this, factory)[TvShowViewModel::class.java]
//    }



    private val onTvClicked = object : OnClickedAction{
        override fun onClicked(id: Int) {
            val intent = Intent(context, TvShowDetailActivity::class.java).apply {
                putExtra(EXTRA_ID, id)
            }
            startActivity(intent)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_show_fragment, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.tv_show)

        tvPagedListAdapter = TvShowPagedListAdapter(onTvClicked)

        responseWatcher(responseLoading)
        loadTvData(TOP_RATED)

        with(rv_tv_show) {
            adapter = tvPagedListAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }

        tab_layout_tv_show.addTab(tab_layout_tv_show.newTab().setText(R.string.top_rated))
        tab_layout_tv_show.addTab(tab_layout_tv_show.newTab().setText(R.string.popular))
        tabTvOnClicked(tab_layout_tv_show)
    }

    private fun tabTvOnClicked(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                responseWatcher(responseLoading)
                loadTvData(tab?.text.toString().toLowerCase(Locale.getDefault()))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun loadTvData(type: String) {
        viewModel.getTvShows(type).observe(viewLifecycleOwner, Observer {
            Timber.d("Size of LoadTvData is ${it.size}")
            if (it != null) tvPagedListAdapter.submitList(it) else responseWatcher(responseEmpty)

            it.addWeakCallback(null, object : PagedList.Callback() {
                override fun onChanged(position: Int, count: Int) {
                    Timber.d("count is $count")
                    if (count > 0) {
                        responseWatcher(responseLoaded)
                    } else {
                        responseWatcher(responseEmpty)
                    }
                }

                override fun onInserted(position: Int, count: Int) {
                }

                override fun onRemoved(position: Int, count: Int) {
                }

            })
            responseWatcher(responseLoaded)
        })
    }

    private fun responseWatcher(status: String) {
        when (status) {
            responseEmpty -> {
                tv_null_response_tv_show.visibility = View.VISIBLE
                img_null_response_tv_show.visibility = View.VISIBLE
                progress_bar_tv_show.visibility = View.GONE
            }
            responseLoading -> {
                tv_null_response_tv_show.visibility = View.GONE
                img_null_response_tv_show.visibility = View.GONE
                progress_bar_tv_show.visibility = View.VISIBLE
            }
            responseLoaded -> {
                tv_null_response_tv_show.visibility = View.GONE
                img_null_response_tv_show.visibility = View.GONE
                progress_bar_tv_show.visibility = View.GONE
            }
        }
    }

}