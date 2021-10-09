package com.ibnu.submission2jetpack.universaladapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibnu.submission2jetpack.BuildConfig
import com.ibnu.submission2jetpack.R
import com.ibnu.submission2jetpack.data.source.model.Cast
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private var listCasts = ArrayList<Cast>()

    fun updateCastData(cast: List<Cast>?) {
        if (cast == null) return
        this.listCasts.clear()
        this.listCasts.addAll(cast)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = listCasts[position]
        holder.bind(cast)
    }

    override fun getItemCount(): Int = listCasts.size

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: Cast) {
            with(itemView) {
                name_cast_item.text = cast.castName
                Glide.with(context)
                    .load("${BuildConfig.POSTER_PATH}${cast.castImage}" )
                    .placeholder(R.drawable.ic_baseline_person_pin_24)
                    .into(img_cast_item)
            }
        }
    }

}