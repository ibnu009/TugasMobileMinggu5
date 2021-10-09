package com.ibnu.submission2jetpack.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.repo.FavoriteRepository
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getFavoriteItems(type: String): LiveData<PagedList<FavoriteItemEntity>> {
        Timber.d("Data is ${favoriteRepository.getAllFavoriteItems(type)}")
       return LivePagedListBuilder(favoriteRepository.getAllFavoriteItems(type), 3).build()
    }

}