package com.ibnu.submission2jetpack.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder

object TypeUtils {
    const val POPULAR = "popular"
    const val TOP_RATED = "top rated"

    const val MOVIE = "Movie"
    const val TV = "Tv Show"

/*    Pada bagian ini adalah untuk sorting type dari data favorite yang tampil terlebih dahulu, disini menggunakan
         Ascending dan Descending karena Memanfaatkan urutan dari Alphabet
*/
    fun getSortedByType(filterType: String): SimpleSQLiteQuery{
        val simpleSQLiteQuery = StringBuilder().append("SELECT * FROM favoriteitementity ")
        when (filterType) {
            MOVIE -> {
                simpleSQLiteQuery.append("ORDER BY favoriteItemType ASC")
            }
            TV ->{
                simpleSQLiteQuery.append("ORDER BY favoriteItemType DESC")
            }
        }
        return SimpleSQLiteQuery(simpleSQLiteQuery.toString())
    }
}