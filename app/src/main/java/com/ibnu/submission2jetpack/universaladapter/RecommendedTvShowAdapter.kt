package com.ibnu.submission2jetpack.universaladapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibnu.submission2jetpack.BuildConfig
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedAction
import com.ibnu.submission2jetpack.utils.GenreConverter
import kotlinx.android.synthetic.main.item_tv_show_recommended.view.*

class RecommendedTvShowAdapter(private val onClickedAction: OnClickedAction) : RecyclerView.Adapter<RecommendedTvShowAdapter.TvShowViewHolder>() {

    private var listTvShow = ArrayList<TvShow>()

    fun updateData(tvShow: List<TvShow>?) {
        if (tvShow == null) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvShow)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder =
        TvShowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show_recommended, parent, false)
        )

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = listTvShow[position]
        holder.bind(tvShow)
        holder.itemView.setOnClickListener {
            onClickedAction.onClicked(tvShow.tvShowId)
        }
    }

    override fun getItemCount(): Int = listTvShow.size

    class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShow) {
            with(itemView) {
                tv_title_tv_show_recommended.text = tvShow.tvShowName

                Glide.with(context)
                    .load("${BuildConfig.POSTER_PATH}${tvShow.tvShowPosterPath}" )
                    .placeholder(R.drawable.ic_television)
                    .into(img_cover_tv_show_recommended)

                tv_category_tv_show_recommended.text = GenreConverter().displayGenre(tvShow.tvShowCategory)
            }
        }
    }

}