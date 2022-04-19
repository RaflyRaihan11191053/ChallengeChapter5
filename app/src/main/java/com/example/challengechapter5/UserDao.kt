package com.example.challengechapter5

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun login(username: String, password: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun register(user: User): Long

    @Update
    fun updateProfile(user: User): Int
}