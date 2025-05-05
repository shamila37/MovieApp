package com.example.moviecw.MovieDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MoviesEntity>)

    @Query("SELECT * FROM movies")
    fun getAll(): List<MoviesEntity>

    @Query("SELECT * FROM movies WHERE title LIKE :title")
    fun findByTitle(title: String): List<MoviesEntity>

    @Query("SELECT * FROM movies WHERE actors LIKE :actor")
    fun findByActor(actor: String): List<MoviesEntity>

    @Query("SELECT * FROM movies WHERE LOWER(actors) LIKE '%' || LOWER(:actorName) || '%'")
    fun searchByActor(actorName: String): List<MoviesEntity>
}


//w1986643/20223147
//Shamila Ashan Gunarathna