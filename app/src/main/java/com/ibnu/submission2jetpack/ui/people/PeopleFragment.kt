package com.ibnu.submission2jetpack.ui.people

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
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.ui.people.adapter.PeoplePagedListAdapter
import com.ibnu.submission2jetpack.utils.NetworkState
import com.ibnu.submission2jetpack.utils.ResponseEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.people_fragment.*

@Suppress("RedundantSamConstructor")
@AndroidEntryPoint
class PeopleFragment : Fragment() {

    companion object{
        val responseLoading = ResponseEnum.LOADING.status
        val responseLoaded = ResponseEnum.LOADED.status
        val responseEmpty = ResponseEnum.EMPTY.status
    }

    private val peopleViewModel : PeopleViewModel by viewModels()

    private lateinit var peopleAdapter: PeoplePagedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.people)

        peopleAdapter = PeoplePagedListAdapter()
        with(rv_people) {
            adapter = peopleAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
        responseWatcher(responseLoading)
        peopleViewModel.getPeople().observe(this.viewLifecycleOwner, Observer { people ->
            peopleAdapter.submitList(people)
            responseWatcher(responseLoaded)
        })

        peopleViewModel.networkState.observe(this.viewLifecycleOwner, Observer {
            if (peopleViewModel.listIsEmpty() && it == NetworkState.LOADING) responseWatcher(responseLoading) else responseWatcher(responseLoaded)
            if (peopleViewModel.listIsEmpty() && it == NetworkState.ERROR) responseWatcher(responseEmpty) else responseWatcher(
                responseLoaded
            )
        })
    }

    private fun responseWatcher(status: String) {
        when (status) {
            responseEmpty -> {
                tv_null_response_people.visibility = View.VISIBLE
                img_null_response_people.visibility = View.VISIBLE
                progress_bar_people.visibility = View.GONE
            }
            responseLoading -> {
                tv_null_response_people.visibility = View.GONE
                img_null_response_people.visibility = View.GONE
                progress_bar_people.visibility = View.VISIBLE
            }
            responseLoaded -> {
                tv_null_response_people.visibility = View.GONE
                img_null_response_people.visibility = View.GONE
                progress_bar_people.visibility = View.GONE
            }
        }
    }
}