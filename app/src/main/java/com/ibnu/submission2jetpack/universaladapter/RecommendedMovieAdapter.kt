package com.ibnu.submission2jetpack.universaladapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibnu.submission2jetpack.BuildConfig
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import kotlinx.android.synthetic.main.item_movie.view.*

class RecommendedMovieAdapter(private val onClickedAction: OnClickedAction) : RecyclerView.Adapter<RecommendedMovieAdapter.MovieViewHolder>() {

    private var listMovie = ArrayList<Movie>()

    fun updateData(movie: List<Movie>?) {
        if (movie == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onClickedAction.onClicked(movie.movieId)
        }
    }

    override fun getItemCount(): Int = listMovie.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                tv_title_movie.text = movie.movieName

                Glide.with(context)
                    .load("${BuildConfig.POSTER_PATH}${movie.moviePosterPath}" )
                    .placeholder(R.drawable.ic_cinema)
                    .into(img_cover_movie)
//                tv_category_movie.text = GenreConverter().displayGenre(movie.movieCategory)
            }
        }
    }
}