package com.ibnu.submission2jetpack.ui.favorite.adapter

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
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import com.ibnu.submission2jetpack.data.source.remote.status.OnClickedActionFavorite
import com.ibnu.submission2jetpack.utils.TypeUtils.MOVIE
import com.ibnu.submission2jetpack.utils.TypeUtils.TV
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoritePagedListAdapter(private val onClickedActionFavorite: OnClickedActionFavorite) :
    PagedListAdapter<FavoriteItemEntity, FavoritePagedListAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        getItem(position)?.let { item->
            holder.bind(item)
            holder.itemView.setOnClickListener {
                onClickedActionFavorite.onClicked(id = item.favoriteItemId, item.favoriteItemType)
            }
        }
    }

    inner class FavoriteViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        fun bind(favorite: FavoriteItemEntity){
            with(itemView) {
                tv_title_favorite.text = favorite.favoriteItemTitle

                Glide.with(context)
                    .load("${BuildConfig.POSTER_PATH}${favorite.favoriteItemPoster}" )
                    .placeholder(R.drawable.ic_cinema)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(img_cover_favorite)

                if (favorite.favoriteItemType == MOVIE) {
                    tv_type_favorite.text = MOVIE
                    tv_type_favorite.setBackgroundResource(R.drawable.circle_bg_movie)
                } else{
                    tv_type_favorite.text = TV
                    tv_type_favorite.setBackgroundResource(R.drawable.circle_bg_tv)
                }

            }
        }
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteItemEntity> = object :
            DiffUtil.ItemCallback<FavoriteItemEntity>() {
            override fun areItemsTheSame(oldItem: FavoriteItemEntity, newItem: FavoriteItemEntity): Boolean {
                return oldItem.favoriteItemId == newItem.favoriteItemId && oldItem.favoriteItemId == newItem.favoriteItemId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FavoriteItemEntity, newItem: FavoriteItemEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}