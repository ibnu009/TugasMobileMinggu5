package com.ibnu.submission2jetpack.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavoriteDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteItems(favoriteItem: FavoriteItemEntity)

    @RawQuery(observedEntities = [FavoriteItemEntity::class])
    fun getFavoriteItems(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteItemEntity>

    @Query("SELECT * FROM favoriteitementity WHERE favoriteItemId= :id")
    fun checkItemsIsFavorite(id: Int): Observable<List<FavoriteItemEntity>>

    @Query("DELETE FROM favoriteitementity WHERE favoriteItemId=:id")
    fun deleteFavoriteItems(id: Int)

}