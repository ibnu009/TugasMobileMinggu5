package com.ibnu.submission2jetpack.data.source.local.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import com.ibnu.submission2jetpack.data.source.local.room.FavoriteDao

@Database(entities = [FavoriteItemEntity::class], version = 6, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        @JvmStatic
        fun getDataBase(context: Context): DataBase {
            if (INSTANCE == null) {
                synchronized(DataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DataBase::class.java,
                            "db_movie"
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE as DataBase
        }
    }
}