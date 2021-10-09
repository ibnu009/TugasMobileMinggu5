package com.ibnu.submission2jetpack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favoriteItemId")
    val favoriteItemId: Int,
    @ColumnInfo(name = "favoriteItemPoster")
    val favoriteItemPoster: String,
    @ColumnInfo(name = "favoriteItemTitle")
    val favoriteItemTitle: String,
    @ColumnInfo(name = "favoriteItemType")
    val favoriteItemType: String
)
