package com.example.moviedb1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movieDB")
    fun getAll(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("DELETE FROM movieDB")
    fun deleteAll()

    @Query("SELECT * FROM movieDB WHERE id=:id")
    fun getMovieById(id: Int): Movie

    @Query("DELETE FROM movieDB WHERE id=:id")
    fun deleteMovieById(id: Int)
}