package com.ibnu.submission2jetpack.ui.movie.adapter

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
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.utils.GenreConverter
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviePagedListAdapter(private val onClickedAction: OnClickedAction) :
    PagedListAdapter<Movie, MoviePagedListAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie->
            holder.bind(movie)
            holder.itemView.setOnClickListener {

                onClickedAction.onClicked(movie.movieId)
            }
        }
    }

    inner class MovieViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        fun bind(movie: Movie){
            with(itemView) {
                tv_title_movie.text = movie.movieName

                Glide.with(context)
                    .load("${BuildConfig.POSTER_PATH}${movie.moviePosterPath}" )
                    .placeholder(R.drawable.ic_cinema)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(img_cover_movie)

                val genre = GenreConverter().displayGenre(movie.movieCategory)
                tv_category_movie.text = genre
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object :
            DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.movieId == newItem.movieId && oldItem.movieName == newItem.movieName
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }


}