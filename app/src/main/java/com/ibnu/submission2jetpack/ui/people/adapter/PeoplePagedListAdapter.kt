package com.ibnu.submission2jetpack.ui.people.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ibnu.submission2jetpack.BuildConfig
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.model.People
import com.ibnu.submission2jetpack.data.source.model.PeopleKnownFor
import kotlinx.android.synthetic.main.item_people.view.*

class PeoplePagedListAdapter :
    PagedListAdapter<People, PeoplePagedListAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        getItem(position)?.let { item->
            holder.bind(item)
        }
    }

    inner class FavoriteViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        fun bind(people: People){
            with(itemView) {
                tv_name_people.text = people.peopleName

                Glide.with(context)
                    .load("${BuildConfig.POSTER_PATH}${people.peopleImg}" )
                    .placeholder(R.drawable.ic_user)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(img_cover_people)
                tv_people_known_for_movie.text = knownForMovies(people.knownFor)
            }
        }
    }

    private fun knownForMovies(list: List<PeopleKnownFor>) : String{
        var knownForName = ""
        for (data in list) {
            if (data.mediaType == "tv"){
                if (list.size == 1){
                    knownForName = data.knownForTvShowName!!
                } else {
                    knownForName += "${data.knownForTvShowName}, "
                }

            } else {
                if (list.size == 1){
                    knownForName = data.knownForMovieName!!
                } else {
                    knownForName += "${data.knownForMovieName}, "
                }
            }
        }
        return knownForName
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<People> = object :
            DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem.peopleId == newItem.peopleId && oldItem.peopleId == newItem.peopleId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem == newItem
            }
        }
    }

}