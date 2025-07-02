package com.example.peoplearoundtheworld.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.peoplearoundtheworld.model.User
import com.example.peoplearoundtheworld.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class DbDataSource: RoomDatabase() {

    abstract fun userDao(): UserDao
}