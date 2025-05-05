package com.example.moviecw.MovieDB

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoviesEntity::class], version = 1)
abstract class MovieAppDB : RoomDatabase() {
    abstract fun moviesDao(): MoviesDAO

    companion object {
//        https://www.baeldung.com/kotlin/volatile-properties
        @Volatile private var INSTANCE: MovieAppDB? = null

        fun getInstance(context: Context): MovieAppDB {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    MovieAppDB::class.java,
                    "movies_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


//w1986643/20223147
//Shamila Ashan Gunarathna