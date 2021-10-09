package com.ibnu.submission2jetpack.ui.tvshow.adapter

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
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.utils.GenreConverter
import kotlinx.android.synthetic.main.item_tv_show.view.*

class TvShowPagedListAdapter(private val onClickedAction: OnClickedAction) :
    PagedListAdapter<TvShow, TvShowPagedListAdapter.TvViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TvViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        getItem(position)?.let { tv->
            holder.bind(tv)
            holder.itemView.setOnClickListener {
                onClickedAction.onClicked(tv.tvShowId)
            }
        }
    }

    inner class TvViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(tv: TvShow){
            with(itemView) {
                tv_title_tv_show.text = tv.tvShowName

                Glide.with(context)
                    .load("${BuildConfig.POSTER_PATH}${tv.tvShowPosterPath}" )
                    .placeholder(R.drawable.ic_cinema)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(img_cover_tv_show)

                val genre = GenreConverter().displayGenre(tv.tvShowCategory)
                tv_category_tv_show.text = genre
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TvShow> = object :
            DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.tvShowId == newItem.tvShowId && oldItem.tvShowName == newItem.tvShowName
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }


}